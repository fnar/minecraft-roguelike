package com.github.fnar.roguelike.dungeon.settings.fixture;

public class Layout {

  public static String layoutSizeSmallSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"layout:size_small\",\n" +
        "  \"inherit\": [\n" +
        "    \"layout:roomcount_small\",\n" +
        "    \"layout:scatter_small\",\n" +
        "    \"layout:type_mixed\"\n" +
        "  ]\n" +
        "}";
  }

  public static String layoutRoomcountSmallSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"layout:roomcount_small\",\n" +
        "  \"numRooms\": [8, 8, 8, 8, 8]\n" +
        "}";
  }

  public static String layoutScatterSmallSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"layout:scatter_small\",\n" +
        "  \"scatter\": [12, 12, 12, 12, 12]\n" +
        "}";
  }

  public static String layoutTypeMixedSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"layout:type_mixed\",\n" +
        "  \"layouts\": [\n" +
        "    {\"level\": [0, 2, 4], \"type\": \"classic\"},\n" +
        "    {\"level\": [1, 3], \"type\": \"mst\"}\n" +
        "  ]\n" +
        "}";
  }
}
