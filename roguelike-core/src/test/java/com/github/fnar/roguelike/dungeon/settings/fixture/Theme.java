package com.github.fnar.roguelike.dungeon.settings.fixture;

public class Theme {

  public static String caveThemeSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\": \"theme:cave\",\n" +
        "  \"themes\": [\n" +
        "    {\n" +
        "      \"base\": \"MOSSY\",\n" +
        "      \"primary\": {\n" +
        "        \"walls\": {\n" +
        "          \"type\": \"WEIGHTED\",\n" +
        "          \"data\": [\n" +
        "            {\"type\": \"METABLOCK\", \"data\": {\"name\": \"stone\"}, \"weight\": 100},\n" +
        "            {\"type\": \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\": 10},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\": 10},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\": 10},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"dirt\"}, \"weight\": 10},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"coal_ore\"}, \"weight\": 5},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"iron_ore\"}, \"weight\": 1}\n" +
        "          ]\n" +
        "        },\n" +
        "        \"floor\" : {\n" +
        "          \"type\" : \"WEIGHTED\",\n" +
        "          \"data\" : [\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass_path\"}, \"weight\": 50},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"dirt\"}, \"weight\": 25},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}, \"weight\": 5},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\": 1},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass\"}, \"weight\": 1}\n" +
        "          ]\n" +
        "        },\n" +
        "        \"pillar\" : {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"planks\", \"meta\" : 0}},\n" +
        "        \"stair\" : {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"oak_stairs\"}}\n" +
        "      },\n" +
        "      \"secondary\" : {\n" +
        "        \"walls\" : {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}},\n" +
        "        \"floor\" : {\n" +
        "          \"type\" : \"WEIGHTED\",\n" +
        "          \"data\" : [\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass_path\"}, \"weight\": 50},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"dirt\"}, \"weight\": 25},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}, \"weight\": 5},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\": 1},\n" +
        "            {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass\"}, \"weight\": 1}\n" +
        "          ]\n" +
        "        },\n" +
        "        \"pillar\" : {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"planks\", \"meta\": 0}},\n" +
        "        \"stair\" : {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"oak_stairs\"}}\n" +
        "      }\n" +
        "    }\n" +
        "  ]\n" +
        "}\n";
  }

  public static String forestThemeSettingsJson() {
    return "" +
        "{\n" +
        "  \"name\" : \"theme:forest\",\n" +
        "  \"themes\" : [\n" +
        "    {\n" +
        "      \"base\": \"DARKOAK\",\n" +
        "      \"level\" : 0,\n" +
        "      \"primary\" : {\n" +
        "        \"lightblock\": {\"type\":  \"METABLOCK\", \"data\":  {\"name\":  \"sea_lantern\"}},\n" +
        "        \"walls\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\", \"meta\" : 0}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 1},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 5},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"web\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        },\n" +
        "        \"pillar\" : {\"type\" : \"LAYERS\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 3}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}}\n" +
        "        ]},\n" +
        "        \"door\": {\"name\": \"dark_oak_door\"},\n" +
        "        \"floor\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 75},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 10},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 15},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass\"}, \"weight\" : 20},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass_path\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        }\n" +
        "      }\n" +
        "    }\n" +
        "  ]\n" +
        "}";
  }
}
