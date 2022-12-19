package com.github.fnar.minecraft.block.spawner;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public enum MobType {

  BAT("bat"),

  BLAZE("blaze"),
  CAVESPIDER("cave_spider"),
  CREEPER("creeper"),
  ELDER_GUARDIAN("elder_guardian"),
  ENDERMAN("enderman"),
  ENDERMITE("endermite"),
  EVOKER("evocation_illager"),
  GHAST("ghast"),
  GUARDIAN("guardian"),
  HUSK("husk"),
  ILLUSIONER("illusion_illager"),
  MAGMA_CUBE("magma_cube"),
  PIGZOMBIE("zombie_pigman"),
  SHULKER("shulker"),
  SILVERFISH("silverfish"),
  SKELETON("skeleton"),
  SLIME("slime"),
  SPIDER("spider"),
  STRAY("stray"),
  VEX("vex"),
  VINDICATOR("vindication_illager"),
  WITCH("witch"),
  WITHERSKELETON("wither_skeleton"),
  ZOMBIE("zombie"),
  ZOMBIE_VILLAGER("zombie_villager"),

  WITHER("wither"),
  DRAGON("dragon"),
  ;

  public static final MobType[] COMMON_MOBS = {SKELETON, SPIDER, ZOMBIE};
  public static final MobType[] UNCOMMON_MOBS = {CAVESPIDER, CREEPER};
  public static final MobType[] RARE_MOBS = {ENDERMAN, SLIME, WITCH};
  public static final MobType[] EPIC_MOBS = {DRAGON, WITHER};
  public static final MobType[] LEGENDARY_MOBS = {};


  public static final MobType[] HUMANOID_MOBS = {SKELETON, WITCH, ZOMBIE};
  public static final MobType[] UNDEAD_MOBS = {SKELETON, ZOMBIE};
  public static final MobType[] NETHER_MOBS = {BLAZE, MAGMA_CUBE, PIGZOMBIE, WITHERSKELETON};
  public static final MobType[] ILLAGERS = {EVOKER, ILLUSIONER, VINDICATOR};

  private final String name;

  MobType(String name) {
    this.name = "minecraft:" + name;
  }

  public static MobType chooseAmong(MobType[] mobTypes, Random random) {
    return mobTypes[random.nextInt(mobTypes.length)];
  }

  public String getName() {
    return name;
  }

  public Spawner asSpawner() {
    return asSpawner(this);
  }

  public static Spawner asSpawner(MobType... mobTypes) {
    return new Spawner(Arrays.stream(mobTypes)
        .map(MobType::getName)
        .map(SpawnPotential::new)
        .collect(Collectors.toList())
    );
  }

}
