package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public class NoveltyLootItemParser {
  public static IWeighted<RldItemStack> parse(JsonObject data, int weight) throws Exception {
    if (!data.has("name")) {
      throw new DungeonSettingParseException("Novelty item requires a name");
    }
    String name = data.get("name").getAsString();
    RldItemStack item = ItemNovelty.getItemByName(name);
    if (item == null) {
      throw new DungeonSettingParseException("No such novelty name: " + name);
    }
    return new WeightedChoice<>(item, weight);
  }
}
