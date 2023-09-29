package com.github.fnar.roguelike.dungeon.settings.fixture;

public class Dungeon {
  public static String dungeonGenericSettingsJson() {
    return "{\n" +
        "  \"name\": \"dungeonGeneric\"" +
        "}";
  }

  public static String dungeonCaveSettingsJson() {
    return "{\n" +
        "  \"name\": \"fnarDungeonCave\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\": [\n" +
        "    \"themeCave\"\n" +
        "  ],\n" +
        "  \"criteria\": {\"biomeTypes\": [\"FOREST\", \"MESA\", \"PLAINS\", \"SANDY\", \"SNOWY\"]}\n" +
        "}\n";
  }

  public static String dungeonCaveSmallSettingsJson() {
    return "{\n" +
        "  \"name\": \"fnarDungeonCaveSmall\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\": [\n" +
        "    \"fnarDungeonCave\",\n" +
        "    \"layoutSizeSmall\"\n" +
        "  ]\n" +
        "}\n";
  }

  public static String dungeonForestTempleSettingsJson() {
    return "{\n" +
        "  \"name\" : \"dungeonForestTemple\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\" : [\n" +
        "    \"dungeonGeneric\",\n" +
        "    \"themeForest\",\n" +
        "    \"dungeonGeneric\"\n" +
        "  ]\n" +
        "}";
  }
}
