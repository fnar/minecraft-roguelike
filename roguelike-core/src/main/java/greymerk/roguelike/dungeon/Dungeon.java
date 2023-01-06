package greymerk.roguelike.dungeon;

import com.github.fnar.minecraft.block.Material;
import com.github.fnar.util.ReportThisIssueException;

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
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;
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

  public static SettingsResolver settingsResolver;

  static {
    try {
      RogueConfig.reload(false);
      initResolver();
    } catch (Exception e) {
      // do nothing
    }
  }

  public static final String MOD_ID = "roguelike";
  private static final Logger logger = LogManager.getLogger(MOD_ID);

  private Coord origin;
  private final List<DungeonLevel> levels = new ArrayList<>();
  private final WorldEditor editor;

  public Dungeon(WorldEditor editor) {
    this.editor = editor;
  }

  public static void initResolver() throws Exception {
    Dungeon.settingsResolver = SettingsResolver.initSettingsResolver();
  }

  public static boolean canSpawnInChunk(int chunkX, int chunkZ, WorldEditor editor) {
    return RogueConfig.DONATURALSPAWN.getBoolean()
        && SpawnCriteria.isValidDimension(getDimension(chunkX, chunkZ, editor))
//        && isVillageChunk(editor, chunkX, chunkZ)
        && isSpawnFrequencyHit(chunkX, chunkZ)
        && isSpawnChanceHit(chunkX, chunkZ);
  }

  private static int getDimension(int chunkX, int chunkZ, WorldEditor editor) {
    final Coord coord = new Coord(chunkX * CHUNK_SIZE, 0, chunkZ * CHUNK_SIZE);
    return editor.getInfo(coord).getDimension();
  }

  private static boolean isSpawnFrequencyHit(int chunkX, int chunkZ) {
    int frequency = getSpawnFrequency();
    return chunkX % frequency == 0
        && chunkZ % frequency == 0;
  }

  private static int getSpawnFrequency() {
    return 3 * Math.max(2, RogueConfig.SPAWNFREQUENCY.getInt());
  }

  private static boolean isSpawnChanceHit(int chunkX, int chunkZ) {
    double spawnChance = RogueConfig.SPAWNCHANCE.getDouble();
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

  public void generate(DungeonSettings dungeonSettings, Coord coord) {
    logger.info("Trying to spawn dungeon with id {} at {}...", dungeonSettings.getId(), coord);
    try {
      Random random = editor.getRandom();

      origin = new Coord(coord.getX(), TOPLEVEL, coord.getZ());

      IntStream.range(0, dungeonSettings.getNumLevels())
          .mapToObj(dungeonSettings::getLevelSettings)
          .map(DungeonLevel::new)
          .forEach(levels::add);

      Arrays.stream(DungeonStage.values())
          .flatMap(stage -> DungeonTaskRegistry.getInstance().getTasks(stage).stream())
          .forEach(task -> performTaskSafely(dungeonSettings, random, task));

      logger.info("Successfully generated dungeon with id {} at {}.", dungeonSettings.getId(), coord);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void performTaskSafely(DungeonSettings dungeonSettings, Random random, greymerk.roguelike.dungeon.tasks.IDungeonTask task) {
    try {
      task.execute(editor, random, this, dungeonSettings);
    } catch (Exception exception) {
      new ReportThisIssueException(exception).printStackTrace();
    }
  }

  public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
    if (canSpawnInChunk(chunkX, chunkZ, editor)) {
      logger.info("Trying to spawn dungeon at chunkX {} and chunkZ {}...", chunkX, chunkZ);
      int x = chunkX * CHUNK_SIZE;
      int z = chunkZ * CHUNK_SIZE;

      selectLocation(rand, x, z)
          .ifPresent(coord ->
              getDungeonSettingsMaybe(coord)
                  .ifPresent(dungeonSettings ->
                      generate(dungeonSettings, coord)));
    }
  }

  private Optional<Coord> selectLocation(Random rand, int x, int z) {
    int attempts = RogueConfig.SPAWN_ATTEMPTS.getInt();
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

    int upperLimit = RogueConfig.UPPERLIMIT.getInt();
    int lowerLimit = RogueConfig.LOWERLIMIT.getInt();
    Coord cursor = new Coord(coord.getX(), upperLimit, coord.getZ());

    return editor.isAirBlock(cursor)
        && canFindStartingCoord(lowerLimit, cursor)
        && isFreeOverhead(cursor)
        && isSolidBelow(cursor);
  }

  private boolean hasStructureTooCloseBy(Coord coord, VanillaStructure structure) {
    int minimumDistanceRequired = RogueConfig.SPAWN_MINIMUM_DISTANCE_FROM_VANILLA_STRUCTURES.getInt();
    Coord structureCoord = editor.findNearestStructure(structure, coord);
    if (structureCoord == null) {
      logger.info("Did not detect structure \"{}\" within {} blocks of potential spawn location {}.", structure.name(), minimumDistanceRequired, coord);
      return false;
    }
    return coord.distance(structureCoord) < minimumDistanceRequired;
  }

  private boolean canFindStartingCoord(int lowerLimit, Coord cursor) {
    while (!editor.isValidGroundBlock(cursor)) {
      cursor.down();
      if (cursor.getY() < lowerLimit) {
        return false;
      }
      if (editor.isMaterialAt(Material.WATER, cursor)) {
        return false;
      }
    }
    return true;
  }

  private boolean isFreeOverhead(Coord cursor) {
    Coord start = cursor.copy().translate(new Coord(-4, 4, -4));
    Coord end = cursor.copy().translate(new Coord(4, 4, 4));

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
    if (settingsResolver == null) {
      return Optional.empty();
    }
    return settingsResolver.chooseDungeonSetting(editor, coord);
  }

  public Coord getPosition() {
    return origin.copy();
  }

  public List<DungeonLevel> getLevels() {
    return levels;
  }
}
