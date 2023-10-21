package com.github.fnar.roguelike.dungeon.settings.fixture;

public class Dungeon {
  public static String dungeonGenericSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"dungeon:generic\"" +
        "}";
  }

  public static String dungeonCaveSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"fnar:dungeon_cave\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\": [\n" +
        "    \"theme:cave\"\n" +
        "  ],\n" +
        "  \"criteria\": {\"biomeTypes\": [\"FOREST\", \"MESA\", \"PLAINS\", \"SANDY\", \"SNOWY\"]}\n" +
        "}\n";
  }

  public static String dungeonCaveSmallSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"fnar:dungeon_cave_small\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\": [\n" +
        "    \"fnar:dungeon_cave\",\n" +
        "    \"layout:size_small\"\n" +
        "  ]\n" +
        "}\n";
  }

  public static String dungeonForestTempleSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\" : \"dungeon:forest_temple\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\" : [\n" +
        "    \"dungeon:generic\",\n" +
        "    \"theme:forest\",\n" +
        "    \"dungeon:generic\"\n" +
        "  ]\n" +
        "}";
  }
}
