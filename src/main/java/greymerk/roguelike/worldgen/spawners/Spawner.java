package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum Spawner {

  BAT("bat"),
  BLAZE("blaze"),
  CAVESPIDER("cave_spider"),
  CREEPER("creeper"),
  ENDERMAN("enderman"),
  GHAST("ghast"),
  LAVASLIME("magma_cube"),
  PIGZOMBIE("zombie_pigman"),
  PRIMEDTNT("tnt"),
  SILVERFISH("silverfish"),
  SKELETON("skeleton"),
  SLIME("slime"),
  SPIDER("spider"),
  WITCH("witch"),
  WITHERBOSS("wither"),
  WITHERSKELETON("wither_skeleton"),
  ZOMBIE("zombie");

  public static final Spawner[] COMMON_MOBS = {SKELETON, SPIDER, ZOMBIE};
  public static final Spawner[] UNCOMMON_MOBS = {CAVESPIDER, CREEPER};
  public static final Spawner[] RARE_MOBS = {ENDERMAN, SLIME, WITCH};
  public static final Spawner[] EPIC_MOBS = {WITHERBOSS};
  public static final Spawner[] LEGENDARY_MOBS = {};


  public static final Spawner[] HUMANOID_MOBS = {SKELETON, WITCH, ZOMBIE};
  public static final Spawner[] UNDEAD_MOBS = {SKELETON, ZOMBIE};
  public static final Spawner[] NETHER_MOBS = {BLAZE, LAVASLIME, PIGZOMBIE, WITHERSKELETON};

  private String name;

  Spawner(String name) {
    this.name = "minecraft:" + name;
  }

  public String getName() {
    return name;
  }

  public static void generate(
      IWorldEditor editor,
      Random rand,
      LevelSettings settings,
      Coord cursor,
      int difficulty,
      Spawner... types
  ) {
    Spawner type = types[rand.nextInt(types.length)];
    SpawnerSettings spawners = settings.getSpawners();
    if (spawners == null) {
      new Spawnable(type).generate(editor, rand, cursor, difficulty);
      return;
    }
    spawners.generate(editor, rand, cursor, type, difficulty);
  }
}
