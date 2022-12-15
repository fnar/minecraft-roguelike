package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.provider.ItemMixture;

public class MixtureParser {

  public static ItemMixture parse(JsonObject data, int weight) {
    if (!data.has("name")) {
      throw new DungeonSettingParseException("Mixture requires a name");
    }
    String name = data.get("name").getAsString();
    try {
      PotionMixture potionMixture = PotionMixture.valueOf(name.toUpperCase());
      return new ItemMixture(weight, potionMixture);
    } catch (Exception e) {
      throw new DungeonSettingParseException("No such Mixture: " + name);
    }
  }
}
