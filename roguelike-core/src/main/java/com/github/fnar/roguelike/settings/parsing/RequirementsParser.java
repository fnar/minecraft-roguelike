package com.github.fnar.roguelike.settings.parsing;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class RequirementsParser {

  private static final String REQUIREMENTS_KEY = "requires";

  public static List<String> parse(JsonObject root) {
    if (!root.has(REQUIREMENTS_KEY)) {
      return Lists.newArrayList();
    }
    JsonElement requiresElement = root.get(REQUIREMENTS_KEY);
    if (requiresElement.isJsonObject()) {
      throw new DungeonSettingParseException("Expected field '" + REQUIREMENTS_KEY + "' to be list of modid's but instead found a single object.");
    }
    if (!requiresElement.isJsonArray()) {
      throw new DungeonSettingParseException("Expected field '" + REQUIREMENTS_KEY + "' to be list of modid's but it wasn't.");
    }
    JsonArray requiresArray = requiresElement.getAsJsonArray();
    List<String> modIdNames = Lists.newArrayList();
    for (JsonElement requiredModElement : requiresArray) {
      modIdNames.add(requiredModElement.getAsString());
    }
    return modIdNames;
  }
}
