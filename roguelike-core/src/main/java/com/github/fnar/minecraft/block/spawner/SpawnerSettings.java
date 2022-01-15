package com.github.fnar.minecraft.block.spawner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;


public class SpawnerSettings {

  private WeightedRandomizer<Spawner> spawners = new WeightedRandomizer<>();

  public SpawnerSettings() {
  }

  public SpawnerSettings(SpawnerSettings toCopy) {
    spawners.merge(toCopy.spawners);
  }

  public SpawnerSettings(SpawnerSettings base, SpawnerSettings other) {
    spawners.merge(base.spawners);
    spawners.merge(other.spawners);
  }

  public WeightedRandomizer<Spawner> getSpawners() {
    return spawners;
  }

  public boolean isEmpty() {
    return spawners.isEmpty();
  }

  public static Spawner parseSpawnPotentials(JsonObject entry) throws Exception {
    // todo: Check for potentials before getting
    JsonElement spawnPotentialsJson = entry.get("potentials");
    List<SpawnPotential> spawnPotentials = SpawnPotentialParser.parse(spawnPotentialsJson);
    return new Spawner(spawnPotentials);
  }

  public static int parseWeight(JsonObject entry) {
    return entry.has("weight")
        ? entry.get("weight").getAsInt()
        : 1;
  }

  public void add(Spawner spawner, int weight) {
    spawners.add(new WeightedChoice<>(spawner, weight));
  }

  @Override
  public String toString() {
    return spawners.toString();
  }

  public Spawner chooseOneAtRandom(Random random) {
    return getSpawners().get(random);
  }
}
