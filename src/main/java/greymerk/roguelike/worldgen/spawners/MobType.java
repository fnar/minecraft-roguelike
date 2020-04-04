package greymerk.roguelike.worldgen.spawners;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public enum MobType {

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

  public static final MobType[] COMMON_MOBS = {SKELETON, SPIDER, ZOMBIE};
  public static final MobType[] UNCOMMON_MOBS = {CAVESPIDER, CREEPER};
  public static final MobType[] RARE_MOBS = {ENDERMAN, SLIME, WITCH};
  public static final MobType[] EPIC_MOBS = {WITHERBOSS};
  public static final MobType[] LEGENDARY_MOBS = {};


  public static final MobType[] HUMANOID_MOBS = {SKELETON, WITCH, ZOMBIE};
  public static final MobType[] UNDEAD_MOBS = {SKELETON, ZOMBIE};
  public static final MobType[] NETHER_MOBS = {BLAZE, LAVASLIME, PIGZOMBIE, WITHERSKELETON};

  private String name;

  MobType(String name) {
    this.name = "minecraft:" + name;
  }

  public String getName() {
    return name;
  }

  public SpawnerSettings newSpawnerSetting() {
    return MobType.newSpawnerSetting(this);
  }

  public static SpawnerSettings newSpawnerSetting(MobType... mobTypes) {
    SpawnerSettings spawnerSettings = new SpawnerSettings();

    Arrays.stream(mobTypes)
        .map(spawner -> new SpawnPotential(spawner.getName(), true, 1, new NBTTagCompound()))
        .map(spawnPotential -> new Spawnable(Lists.newArrayList(spawnPotential)))
        .forEach(spawnable -> spawnerSettings.add(spawnable, 1));

    return spawnerSettings;
  }

}
