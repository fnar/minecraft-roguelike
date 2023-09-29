package com.github.fnar.roguelike.dungeon.settings.fixture;

public class Layout {

  public static String layoutSizeSmallSettingsJson() {
    return "{\n" +
        "  \"name\": \"layoutSizeSmall\",\n" +
        "  \"inherit\": [\n" +
        "    \"layoutRoomCountSmall\",\n" +
        "    \"layoutScatterSmall\",\n" +
        "    \"layoutTypeMixed\"\n" +
        "  ]\n" +
        "}";
  }

  public static String layoutRoomcountSmallSettingsJson() {
    return "{\n" +
        "  \"name\": \"layoutRoomCountSmall\",\n" +
        "  \"numRooms\": [8, 8, 8, 8, 8]\n" +
        "}";
  }

  public static String layoutScatterSmallSettingsJson() {
    return "{\n" +
        "  \"name\": \"layoutScatterSmall\",\n" +
        "  \"scatter\": [12, 12, 12, 12, 12]\n" +
        "}";
  }

  public static String layoutTypeMixedSettingsJson() {
    return "{\n" +
        "  \"name\": \"layoutTypeMixed\",\n" +
        "  \"layouts\": [\n" +
        "    {\"level\": [0, 2, 4], \"type\": \"classic\"},\n" +
        "    {\"level\": [1, 3], \"type\": \"mst\"}\n" +
        "  ]\n" +
        "}";
  }
}
