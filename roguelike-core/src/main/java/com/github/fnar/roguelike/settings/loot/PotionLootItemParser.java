package com.github.fnar.roguelike.settings.loot;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.Potion;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class PotionLootItemParser {

  public Potion parsePotion(JsonObject data) throws DungeonSettingParseException {
    if (!data.has("name")) {
      throw new DungeonSettingParseException("Potion missing name field");
    }

    String name = data.get("name").getAsString().toUpperCase();

    Potion.Form form = parseForm(data);

    return Potion.newPotion()
        .withEffect(parseEffect(name))
        .withExtension(parseIsExtended(name))
        .withAmplification(parseIsStrong(name))
        .withForm(form);
  }

  private Potion.Effect parseEffect(String name) {
    String cleanedName = name
        .replace("LONG_", "")
        .replace("STRONG_", "");
    return Potion.Effect.valueOf(cleanedName);
  }

  private boolean parseIsExtended(String name) {
    return name.contains("LONG");
  }

  private boolean parseIsStrong(String name) {
    return name.contains("STRONG");
  }

  private Potion.Form parseForm(JsonObject data) {
    return data.has("form")
        ? Potion.Form.valueOf(data.get("form").getAsString().toUpperCase())
        : Potion.Form.REGULAR;
  }
}
