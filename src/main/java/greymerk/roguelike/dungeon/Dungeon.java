package greymerk.roguelike.dungeon;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsRandom;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;
import static java.util.Optional.ofNullable;

public class Dungeon {
  public static final int VERTICAL_SPACING = 10;
  public static final int TOPLEVEL = 50;

  public static SettingsResolver settingsResolver;

  static {
    try {
      RogueConfig.reload(false);
      initResolver();
    } catch (Exception e) {
      // do nothing
    }
  }

  private Coord origin;
  private List<DungeonLevel> levels = new ArrayList<>();
  private WorldEditor editor;

  public Dungeon(WorldEditor editor) {
    this.editor = editor;
  }

  public static void initResolver() throws Exception {
    SettingsResolver settingsResolver = SettingsResolver.initSettingsResolver();
    Dungeon.settingsResolver = settingsResolver;
  }

  public static boolean canSpawnInChunk(int chunkX, int chunkZ, WorldEditor editor) {
    return RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)
        && SpawnCriteria.isValidDimension(getDimension(chunkX, chunkZ, editor))
        && isVillageChunk(editor, chunkX, chunkZ)
        && isSpawnChanceHit(chunkX, chunkZ);
  }

  private static int getDimension(int chunkX, int chunkZ, WorldEditor editor) {
    final Coord coord = new Coord(chunkX * 16, 0, chunkZ * 16);
    return editor.getInfo(coord).getDimension();
  }

  public static boolean isVillageChunk(WorldEditor editor, int chunkX, int chunkZ) {
    int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
    int min = 8 * frequency / 10;
    int max = 32 * frequency / 10;

    min = max(min, 2);
    max = max(max, 8);

    int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
    int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;

    int m = tempX / max;
    int n = tempZ / max;

    Random r = editor.getSeededRandom(m, n, 10387312);

    m *= max;
    n *= max;

    m += r.nextInt(max - min);
    n += r.nextInt(max - min);

    return chunkX == m && chunkZ == n;
  }

  private static boolean isSpawnChanceHit(int chunkX, int chunkZ) {
    double spawnChance = RogueConfig.getDouble(RogueConfig.SPAWNCHANCE);
    Random rand = new Random(Objects.hash(chunkX, chunkZ, 31));
    return rand.nextFloat() < spawnChance;
  }

  public static int getLevel(int y) {
    if (y < 15) {
      return 4;
    } else if (y < 25) {
      return 3;
    } else if (y < 35) {
      return 2;
    } else if (y < 45) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Coord getNearbyCoord(Random random, int x, int z, int min, int max) {
    int distance = min + random.nextInt(max - min);
    double angle = random.nextDouble() * 2 * PI;
    int xOffset = (int) (cos(angle) * distance);
    int zOffset = (int) (sin(angle) * distance);
    return new Coord(x + xOffset, 0, z + zOffset);
  }

  private Optional<Coord> selectLocation(Random rand, int x, int z) {
    return IntStream.range(0, 50)
        .mapToObj(i -> getNearbyCoord(rand, x, z, 40, 100))
        .filter(this::canGenerateDungeonHere)
        .findFirst();
  }

  private void printDungeonName(DungeonSettings dungeonSettings) {
    Optional<SettingIdentifier> id = ofNullable(dungeonSettings.getId());
    String string = id.isPresent() ? id.toString() : dungeonSettings.toString();
    Minecraft.getMinecraft().player.sendChatMessage(string);
  }

  public void generate(DungeonSettings dungeonSettings, Coord coord) {
    try {
      Random random = editor.getRandom();

      origin = new Coord(coord.getX(), TOPLEVEL, coord.getZ());
      Coord start = getPosition();
      IntStream.range(0, dungeonSettings.getNumLevels())
          .mapToObj(dungeonSettings::getLevelSettings)
          .map(DungeonLevel::new)
          .forEach(levels::add);

      Arrays.stream(DungeonStage.values())
          .flatMap(stage -> DungeonTaskRegistry.getTaskRegistry().getTasks(stage).stream())
          .forEach(task -> task.execute(editor, random, this, dungeonSettings));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
    if (canSpawnInChunk(chunkX, chunkZ, editor)) {
      int x = chunkX * 16 + 4;
      int z = chunkZ * 16 + 4;

      selectLocation(rand, x, z)
          .ifPresent(coord ->
              getDungeonSettingsMaybe(coord)
                  .ifPresent(dungeonSettings ->
                      generate(dungeonSettings, coord)));
    }
  }

  private Optional<DungeonSettings> getDungeonSettingsMaybe(Coord coord) {
    if (RogueConfig.getBoolean(RogueConfig.RANDOM)) {
      return Optional.of(new SettingsRandom(editor.getRandom()));
    } else if (settingsResolver != null) {
      return Optional.ofNullable(settingsResolver.getAnyCustomDungeonSettings(editor, coord));
    } else {
      return Optional.empty();
    }
  }

  public boolean canGenerateDungeonHere(Coord coord) {
    if (isInvalidBiome(coord) || hasStrongholdTooCloseBy(coord)) {
      return false;
    }

    int upperLimit = RogueConfig.getInt(RogueConfig.UPPERLIMIT);
    int lowerLimit = RogueConfig.getInt(RogueConfig.LOWERLIMIT);
    Coord cursor = new Coord(coord.getX(), upperLimit, coord.getZ());

    return editor.isAirBlock(cursor)
        && canFindStartingCoord(lowerLimit, cursor)
        && isFreeOverhead(cursor)
        && isSolidBelow(cursor);
  }

  private boolean isInvalidBiome(Coord coord) {
    Biome biome = editor.getInfo(coord).getBiome();

    Type[] invalidBiomes = new Type[]{
        Type.RIVER,
        Type.BEACH,
        Type.MUSHROOM,
        Type.OCEAN
    };

    return Arrays.stream(invalidBiomes)
        .anyMatch(type -> BiomeDictionary.hasType(biome, type));
  }

  private boolean hasStrongholdTooCloseBy(Coord coord) {
    Coord stronghold = editor.findNearestStructure(VanillaStructure.STRONGHOLD, coord);
    if (stronghold == null) {
      return false;
    }
    double strongholdDistance = coord.distance(stronghold);
    return strongholdDistance < 300;
  }

  private boolean canFindStartingCoord(int lowerLimit, Coord cursor) {
    while (!editor.validGroundBlock(cursor)) {
      cursor.translate(Cardinal.DOWN);
      if (cursor.getY() < lowerLimit) {
        return false;
      }
      if (editor.getBlock(cursor).getMaterial() == Material.WATER) {
        return false;
      }
    }
    return true;
  }

  private boolean isFreeOverhead(Coord cursor) {
    Coord start = new Coord(cursor).translate(new Coord(-4, 4, -4));
    Coord end = new Coord(cursor).translate(new Coord(4, 4, 4));

    for (Coord c : new RectSolid(start, end)) {
      if (editor.validGroundBlock(c)) {
        return false;
      }
    }
    return true;
  }

  private boolean isSolidBelow(Coord cursor) {
    Coord start1 = new Coord(cursor).translate(new Coord(-4, -3, -4));
    Coord end1 = new Coord(cursor).translate(new Coord(4, -3, 4));
    int airCount = 0;
    for (Coord c : new RectSolid(start1, end1)) {
      if (!editor.validGroundBlock(c)) {
        airCount++;
      }
      if (airCount > 8) {
        return false;
      }
    }
    return true;
  }

  public Coord getPosition() {
    return new Coord(origin);
  }

  public List<DungeonLevel> getLevels() {
    return levels;
  }
}
