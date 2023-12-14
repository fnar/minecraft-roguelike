package greymerk.roguelike.dungeon;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.util.ReportThisIssueException;
import com.github.fnar.util.TimedTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsRandom;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import greymerk.roguelike.dungeon.tasks.IDungeonTask;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Dungeon {

  public static final int NUM_LAYERS = 5;
  public static final int VERTICAL_SPACING = 10;
  public static final int TOPLEVEL = 50;
  public static final int CHUNK_SIZE = 16;
  public static final int BOTTOM_OF_WORLD_HEIGHT = 5;

  public static final String MOD_ID = "roguelike";
  private static final Logger logger = LogManager.getLogger(MOD_ID);

  private Coord origin;
  private final List<DungeonLevel> levels = new ArrayList<>();
  private final WorldEditor editor;

  public Dungeon(WorldEditor editor) {
    this.editor = editor;
    try {
      RogueConfig.reload(false);
      SettingsResolver.getInstance(editor.getModLoader());
    } catch (Exception e) {
      // do nothing
    }
  }

  public static void generateInChunkIfPossible(WorldEditor editor, int chunkX, int chunkZ) {
    if (!isDungeonChunk(editor, chunkX, chunkZ)) {
      return;
    }
    logger.info("Trying to spawn dungeon at chunkX {} and chunkZ {}...", chunkX, chunkZ);
    Dungeon dungeon = new Dungeon(editor);
    Optional<Coord> coord = dungeon.selectLocation(editor.getRandom(), chunkX * CHUNK_SIZE, chunkZ * CHUNK_SIZE);
    if (!coord.isPresent()) {
      return;
    }

    Optional<DungeonSettings> settings = dungeon.getDungeonSettingsMaybe(coord.get());
    if (!settings.isPresent()) {
      return;
    }

    dungeon.timedGenerate(settings.get(), coord.get());
  }

  public static boolean isDungeonChunk(WorldEditor editor, int chunkX, int chunkZ) {
    return RogueConfig.DUNGEONS_SPAWN_ENABLED.getBoolean()
        && SpawnCriteria.isValidDimension(editor.getDimension())
//        && isVillageChunk(editor, chunkX, chunkZ)
        && isSpawnFrequencyHit(chunkX, chunkZ)
        && isSpawnChanceHit(chunkX, chunkZ);
  }

  private static boolean isSpawnFrequencyHit(int chunkX, int chunkZ) {
    int frequency = getSpawnFrequency();
    return chunkX % frequency == 0
        && chunkZ % frequency == 0;
  }

  private static int getSpawnFrequency() {
    return 3 * Math.max(2, RogueConfig.DUNGEONS_SPAWN_FREQUENCY.getInt());
  }

  private static boolean isSpawnChanceHit(int chunkX, int chunkZ) {
    double spawnChance = RogueConfig.DUNGEONS_SPAWN_CHANCE.getDouble();
    Random rand = new Random(Objects.hash(chunkX, chunkZ, 31));
    return rand.nextFloat() < spawnChance;
  }

  public static int getLevel(int y) {
    if (y >= 45) {
      return 0;
    } else if (y >= 35) {
      return 1;
    } else if (y >= 25) {
      return 2;
    } else if (y >= 15) {
      return 3;
    } else {
      return 4;
    }
  }

  public void timedGenerate(DungeonSettings dungeonSettings, Coord coord) {
    new TimedTask("Dungeon.generate()", () -> generate(dungeonSettings, coord)).run();
  }

  public void generate(DungeonSettings dungeonSettings, Coord coord) {
    logger.info("Trying to spawn dungeon with id {} at {}...", dungeonSettings.getId(), coord);
    try {

      origin = coord.copy().setY(TOPLEVEL);

      IntStream.range(0, dungeonSettings.getNumLevels())
          .mapToObj(dungeonSettings::getLevelSettings)
          .map(DungeonLevel::new)
          .forEach(levels::add);

      Arrays.stream(DungeonStage.values())
          .flatMap(stage -> DungeonTaskRegistry.getInstance().getTasks(stage).stream())
          .forEach(task -> performTaskSafely(dungeonSettings, task));

      logger.info("Successfully generated dungeon with id {} at {}.", dungeonSettings.getId(), coord);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void performTaskSafely(DungeonSettings dungeonSettings, IDungeonTask task) {
    try {
      task.execute(editor, this, dungeonSettings);
    } catch (Exception exception) {
      new ReportThisIssueException(exception).printStackTrace();
    }
  }

  private Optional<Coord> selectLocation(Random rand, int x, int z) {
    int attempts = RogueConfig.DUNGEONS_SPAWN_ATTEMPTS.getInt();
    return IntStream.range(0, attempts)
        .mapToObj(i -> getNearbyCoord(rand, x, z))
        .filter(this::canGenerateDungeonHere)
        .findFirst();
  }

  private static Coord getNearbyCoord(Random random, int x, int z) {
    int distance = random.nextInt(getSpawnRadius());
    double angle = random.nextDouble() * 2 * PI;
    int xOffset = (int) (cos(angle) * distance);
    int zOffset = (int) (sin(angle) * distance);
    return new Coord(x + xOffset, 0, z + zOffset);
  }

  private static int getSpawnRadius() {
    int spawnDiameter = getSpawnFrequency() * CHUNK_SIZE;
    return spawnDiameter / 2;
  }

  public boolean canGenerateDungeonHere(Coord coord) {
    Predicate<VanillaStructure> isTooCloseTo = structure -> hasStructureTooCloseBy(coord, structure);
    Set<VanillaStructure> structuresToCheckDistanceTo = RogueConfig.vanillaStructuresToCheckDistanceTo();
    if (!structuresToCheckDistanceTo.isEmpty() && structuresToCheckDistanceTo.stream().anyMatch(isTooCloseTo)) {
      return false;
    }

    Coord cursor = coord.copy().setY(RogueConfig.UPPERLIMIT.getInt());

    return editor.isAirBlock(cursor)
        && canFindStartingCoord(cursor)
        && isFreeOverhead(cursor)
        && isSolidBelow(cursor);
  }

  private boolean hasStructureTooCloseBy(Coord coord, VanillaStructure structure) {
    int minimumDistanceRequired = RogueConfig.SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.getInt();
    Coord structureCoord = editor.findNearestStructure(structure, coord, minimumDistanceRequired);
    if (structureCoord == null) {
      logger.info("Did not detect structure \"{}\" within {} blocks of potential spawn location {}.", structure.name(), minimumDistanceRequired, coord);
      return false;
    }
    return coord.distance(structureCoord) < minimumDistanceRequired;
  }

  private boolean canFindStartingCoord(Coord cursor) {
    while (!editor.isValidGroundBlock(cursor)) {
      cursor.down();
      if (cursor.getY() < RogueConfig.LOWERLIMIT.getInt()) {
        return false;
      }
      //if (editor.isMaterialAt(Material.WATER, cursor)) {
      if (editor.isBlockOfTypeAt(BlockType.WATER_STILL,cursor)) {
        return false;
      }
    }
    return true;
  }

  private boolean isFreeOverhead(Coord cursor) {
    Coord start = cursor.copy().up(4).translate(new Coord(-4, 0, -4));
    Coord end = cursor.copy().up(4).translate(new Coord(4, 0, 4));

    for (Coord c : RectSolid.newRect(start, end)) {
      if (editor.isValidGroundBlock(c)) {
        return false;
      }
    }
    return true;
  }

  private boolean isSolidBelow(Coord cursor) {
    Coord start1 = cursor.copy().translate(new Coord(-4, -3, -4));
    Coord end1 = cursor.copy().translate(new Coord(4, -3, 4));
    int airCount = 0;
    for (Coord c : RectSolid.newRect(start1, end1)) {
      if (!editor.isValidGroundBlock(c)) {
        airCount++;
      }
      if (airCount > 8) {
        return false;
      }
    }
    return true;
  }

  private Optional<DungeonSettings> getDungeonSettingsMaybe(Coord coord) {
    if (RogueConfig.RANDOM.getBoolean()) {
      return Optional.of(new SettingsRandom(editor.getRandom()));
    }
    return SettingsResolver.getInstance(editor.getModLoader()).chooseRandom(editor, coord);
  }

  public Coord getPosition() {
    return origin.copy();
  }

  public List<DungeonLevel> getLevels() {
    return levels;
  }

  public void generateLayout(WorldEditor editor) {
    Coord start = getPosition();
    for (DungeonLevel level : getLevels()) {
      start = level.generateLayout(editor, start).down(Dungeon.VERTICAL_SPACING);
    }
  }

  public void encase(WorldEditor editor) {
    if (RogueConfig.ENCASE.getBoolean()) {
      getLevels().forEach(level -> level.filter(editor, Filter.get(Filter.ENCASE)));
    }
  }

  public void tunnel(WorldEditor editor) {
    getLevels().forEach(level -> level.tunnel(editor));
  }

  public void generateSegments(WorldEditor editor) {
    getLevels().forEach(level -> level.generateSegments(editor));
  }

  public void generateRooms() {
    getLevels().forEach(DungeonLevel::generateRooms);
  }

  public void linkLevels(WorldEditor editor) {
    getLevels().stream().reduce(null, (prev, level) -> level.generateLinkers(editor, prev));
  }

  public void generateTower(WorldEditor editor, DungeonSettings settings) {
    TowerType tower = settings.getTower().getType();
    Theme theme = settings.getTower().getTheme();
    Coord at = getPosition();
    tower.instantiate(editor, theme).generate(at);
  }

  public void applyFilters(WorldEditor editor) {
    getLevels().forEach(level -> level.applyFilters(editor));
  }
}
