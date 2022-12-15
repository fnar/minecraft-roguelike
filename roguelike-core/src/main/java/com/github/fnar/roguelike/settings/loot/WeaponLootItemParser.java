package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class WeaponLootItemParser {

  public static WeaponLootItem parse(JsonObject data, int weight) {
    return new WeaponLootItem(
        weight,
        parseLevel(data),
        parseEquipment(data),
        parseQuality(data),
        parseIsEnchanted(data));
  }

  private static int parseLevel(JsonObject data) {
    if (!data.has("level")) {
      throw new DungeonSettingParseException("Weapon Loot requires a level");
    }
    return data.get("level").getAsInt();
  }

  private static Equipment parseEquipment(JsonObject data) {
    if (!data.has("equipment")) {
      return null;
    }
    try {
      return Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
    } catch (Exception e) {
      throw new DungeonSettingParseException("No such Equipment as: " + data.get("equipment").getAsString());
    }
  }

  private static Quality parseQuality(JsonObject data) {
    if (!data.has("quality")) {
      return null;
    }
    try {
      return Quality.valueOf(data.get("quality").getAsString().toUpperCase());
    } catch (Exception e) {
      throw new DungeonSettingParseException("No such Quality as: " + data.get("quality").getAsString());
    }
  }

  private static boolean parseIsEnchanted(JsonObject data) {
    return !data.has("ench") || data.get("ench").getAsBoolean();
  }

}
