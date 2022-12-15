package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.StringlyNamedItem;

import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;

public class MinecraftItemLootItemParser {

  public static MinecraftItemLootItem parse(JsonObject json, int weight) {
    String name = json.get("name").getAsString();
    int damage = json.has("meta") ? json.get("meta").getAsInt() : 0;
    int enchLevel = json.has("ench") ? json.get("ench").getAsInt() : 0;

    boolean hasMaxAndMin = json.has("min") && json.has("max");
    int min = hasMaxAndMin ? json.get("min").getAsInt() : 1;
    int max = hasMaxAndMin ? json.get("max").getAsInt() : 1;

    String nbt = null;
    if (json.has("nbt")) {
      nbt = json.get("nbt").getAsString();
    }

    // TODO: migrate nbt and enchanting level onto StringlyNamedItem, or into RldBaseItem or something
    return new MinecraftItemLootItem(new StringlyNamedItem(name), damage, min, max, weight)
        .withEnchantmentsOfLevel(enchLevel)
        .withNbt(nbt);
  }

}
