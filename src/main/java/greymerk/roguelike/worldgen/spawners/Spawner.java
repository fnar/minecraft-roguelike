package greymerk.roguelike.worldgen.spawners;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

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

  public SpawnerSettings newSpawnerSetting() {
    return Spawner.newSpawnerSetting(this);
  }

  public static SpawnerSettings newSpawnerSetting(Spawner... spawners) {
    SpawnerSettings spawnerSettings = new SpawnerSettings();

    Arrays.stream(spawners)
        .map(spawner -> new SpawnPotential(spawner.getName(), true, 1, new NBTTagCompound()))
        .map(spawnPotential -> new Spawnable(Lists.newArrayList(spawnPotential)))
        .forEach(spawnable -> spawnerSettings.add(spawnable, 1));

    return spawnerSettings;
  }

}
