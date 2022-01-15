package com.github.fnar.minecraft.block.spawner;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

class SpawnPotentialParser {

  public static List<SpawnPotential> parse(JsonElement data) throws Exception {
    List<SpawnPotential> spawnPotentials = Lists.newArrayList();
    for (JsonElement spawnPotentialJson : data.getAsJsonArray()) {
      if (spawnPotentialJson.isJsonNull()) {
        continue;
      }
      SpawnPotential spawnPotential = parse(spawnPotentialJson.getAsJsonObject());
      spawnPotentials.add(spawnPotential);
    }
    return spawnPotentials;
  }

  private static SpawnPotential parse(JsonObject entry) throws Exception {
    if (!entry.has("name")) {
      throw new Exception("Spawn potential missing name");
    }
    String name = entry.get("name").getAsString();
    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
    boolean equip = entry.has("equip") && entry.get("equip").getAsBoolean();
    String nbt = entry.has("nbt") ? entry.get("nbt").getAsString() : "";
    return new SpawnPotential(name).withEquip(equip).withWeight(weight).withNbt(nbt);
  }
}
