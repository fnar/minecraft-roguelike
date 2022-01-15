package com.github.fnar.minecraft.block.spawner;

import java.util.Random;

import greymerk.roguelike.monster.Mob;

public class SpawnPotential {

  private final String name;
  private int weight = 1;
  private boolean equip = true;
  private String nbtAsJsonString = "{}";

  public SpawnPotential(String name) {
    this.name = name;
  }

  public Mob generateMob(Random random, int level) {
    Mob mob = new Mob();
    return isEquip() ? mob.withRandomEquipment(level, random) : mob;
  }

  public SpawnPotential withWeight(int weight) {
    this.weight = weight;
    return this;
  }

  public SpawnPotential withEquip(boolean equip) {
    this.equip = equip;
    return this;
  }

  public SpawnPotential withNbt(String nbtAsJsonString) {
    this.nbtAsJsonString = nbtAsJsonString;
    return this;
  }

  public String getName() {
    return name;
  }

  public int getWeight() {
    return weight;
  }

  public boolean isEquip() {
    return equip;
  }

  public String getNbt() {
    return this.nbtAsJsonString;
  }
}
