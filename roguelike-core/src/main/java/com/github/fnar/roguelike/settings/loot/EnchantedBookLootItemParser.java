package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import greymerk.roguelike.treasure.loot.provider.EnchantedBookLootItem;

public class EnchantedBookLootItemParser {

  static EnchantedBookLootItem parse(JsonObject data, int weight) {
    int level = data.has("level") ? data.get("level").getAsInt() : 0;
    int enchantmentLevel = data.has("ench") ? data.get("ench").getAsInt() : 0;
    return new EnchantedBookLootItem(weight, level, enchantmentLevel);
  }

}
