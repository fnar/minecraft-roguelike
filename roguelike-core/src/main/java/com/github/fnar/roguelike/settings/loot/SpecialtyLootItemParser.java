package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.SpecialtyLootItem;

public class SpecialtyLootItemParser {

  public static SpecialtyLootItem parse(JsonObject data, int weight) {
    if (!data.has("level")) {
      throw new DungeonSettingParseException("Item requires a level");
    }
    int level = data.get("level").getAsInt();

    Quality quality = null;
    if (data.has("quality")) {
      try {
        quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new DungeonSettingParseException("No such Quality as: " + data.get("quality").getAsString());
      }
    }

    Equipment equipment = null;
    if (data.has("equipment")) {
      try {
        equipment = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new DungeonSettingParseException("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }
    return new SpecialtyLootItem(weight, level, equipment, quality);
  }
}
