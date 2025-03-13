package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.StringlyNamedItem;

import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;

public class MinecraftItemLootItemParser {

  public static MinecraftItemLootItem parse(JsonObject json, int weight) {
    return new MinecraftItemLootItem(
        new StringlyNamedItem(parseName(json)),
        parseDamage(json),
        parseMin(json),
        parseMax(json),
        weight
    )
        .withEnchantmentsOfLevel(parseEnchantmentLevel(json))
        .withNbt(parseNbt(json));
  }

  private static String parseNbt(JsonObject json) {
    String nbt = null;
    if (json.has("nbt")) {
      nbt = json.get("nbt").getAsString();
    }
    return nbt;
  }

  private static String parseName(JsonObject json) {
    return json.get("name").getAsString();
  }

  private static int parseDamage(JsonObject json) {
    return json.has("meta") ? json.get("meta").getAsInt() : 0;
  }

  private static int parseMin(JsonObject json) {
    return hasMaxAndMin(json) ? json.get("min").getAsInt() : 1;
  }

  private static int parseMax(JsonObject json) {
    return hasMaxAndMin(json) ? json.get("max").getAsInt() : 1;
  }

  private static boolean hasMaxAndMin(JsonObject json) {
    return json.has("min") && json.has("max");
  }

  private static int parseEnchantmentLevel(JsonObject json) {
    return json.has("ench") ? json.get("ench").getAsInt() : 0;
  }

}
