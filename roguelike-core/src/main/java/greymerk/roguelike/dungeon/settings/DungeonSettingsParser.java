package greymerk.roguelike.dungeon.settings;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.block.spawner.SpawnerSettings;
import com.github.fnar.roguelike.settings.RequiredModMissingException;

import net.minecraftforge.fml.common.Loader;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.LayoutGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.rooms.RoomSettingParser;
import greymerk.roguelike.dungeon.settings.level.LevelsParser;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeParser;
import greymerk.roguelike.treasure.loot.LootRulesParser;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.filter.Filter;

public class DungeonSettingsParser {

  public static final List<Integer> ALL_LEVELS = Collections.unmodifiableList(Lists.newArrayList(0, 1, 2, 3, 4));

  private static final String REQUIRES_KEY = "requires";

  public static Optional<DungeonSettings> parseJson(String content) throws Exception {
    try {
      JsonParser jsonParser = new JsonParser();
      JsonObject parse;
      try {
        parse = jsonParser.parse(content).getAsJsonObject();
      } catch (JsonSyntaxException e) {
        throw new Exception(e.getCause());
      }
      return parseDungeonSettings(parse);
    } catch (DungeonSettingParseException | RequiredModMissingException exception) {
      throw exception;
    } catch (Exception e) {
      throw new Exception("An unknown error occurred while parsing json: " + e.getClass().toString() + " " + e.getMessage());
    }
  }

  public static Optional<DungeonSettings> parseDungeonSettings(JsonObject root) throws Exception {
    boolean hasRequiredMods = parseRequires(root);
    if (!hasRequiredMods) {
      return Optional.empty();
    }

    DungeonSettings dungeonSettings = new DungeonSettings();
    // set up level settings objects
    for (int level = 0; level < DungeonSettings.MAXIMUM_COUNT_OF_LEVELS; ++level) {
      LevelSettings setting = new LevelSettings(level);
      dungeonSettings.getLevelSettings().put(level, setting);
    }

    parseId(root, dungeonSettings);
    parseExclusive(root, dungeonSettings);
    parseCriteria(root, dungeonSettings);
    parseTower(root, dungeonSettings);
    parseLootRules(root, dungeonSettings);
    parseOverrides(root, dungeonSettings);
    parseInherit(root, dungeonSettings);
    parseLootTables(root, dungeonSettings);
    parseRoomCount(root, dungeonSettings);
    parseScatter(root, dungeonSettings);
    parseLayouts(root, dungeonSettings);
    parseSpawners(root, dungeonSettings);
    parseRooms(root, dungeonSettings);
    parseThemes(root, dungeonSettings);
    parseSegments(root, dungeonSettings);
    parseFilters(root, dungeonSettings);
    return Optional.of(dungeonSettings);
  }

  private static boolean parseRequires(JsonObject root) throws Exception {

    if (!root.has(REQUIRES_KEY)) {
      return true;
    }
    JsonElement requiresElement = root.get(REQUIRES_KEY);

    if (requiresElement.isJsonObject()) {
      throw new DungeonSettingParseException("Expected field '" + REQUIRES_KEY + "' to be list of modid's but instead found a single object.");
    }
    if (!requiresElement.isJsonArray()) {
      throw new DungeonSettingParseException("Expected field '" + REQUIRES_KEY + "' to be list of modid's but it wasn't.");
    }
    JsonArray requiresArray = requiresElement.getAsJsonArray();
    for (JsonElement requiredModElement : requiresArray) {
      String requiredModName = requiredModElement.getAsString();
      if (!Loader.isModLoaded(requiredModName)) {
        if (RogueConfig.BREAK_IF_REQUIRED_MOD_IS_MISSING.getBoolean()) {
          throw new RequiredModMissingException(requiredModName);
        }
        return false;
      }
    }
    return true;
  }

  private static void parseId(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (!root.has("name")) {
      throw new Exception("Setting missing name");
    }

    if (root.has("namespace")) {
      String name = root.get("name").getAsString();
      String namespace = root.get("namespace").getAsString();
      dungeonSettings.setId(new SettingIdentifier(namespace, name));
    } else {
      dungeonSettings.setId(new SettingIdentifier(root.get("name").getAsString()));
    }
  }

  private static void parseExclusive(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("exclusive")) {
      dungeonSettings.setExclusive(root.get("exclusive").getAsBoolean());
    }
  }

  private static void parseCriteria(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("criteria")) {
      dungeonSettings.setSpawnCriteria(new SpawnCriteria(root.get("criteria").getAsJsonObject()));
    }
  }

  private static void parseTower(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (root.has("tower")) {
      dungeonSettings.setTowerSettings(new TowerSettings(root.get("tower")));
    }
  }

  private static void parseLootRules(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (root.has("loot_rules")) {
      throw new DungeonSettingParseException("Please update field \"loot_rules\" to \"lootRules\" to conform to JSON standards.");
    }
    if (root.has("lootRules")) {
      dungeonSettings.getLootRules().addAll(new LootRulesParser().parseLootRules(root.get("lootRules")));
    }
  }

  private static void parseOverrides(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("overrides")) {
      JsonArray overrides = root.get("overrides").getAsJsonArray();
      for (JsonElement jsonElement : overrides) {
        if (jsonElement.isJsonNull()) {
          continue;
        }
        dungeonSettings.getOverrides().add(SettingsType.valueOf(jsonElement.getAsString()));
      }
    }
  }

  private static void parseInherit(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("inherit")) {
      JsonArray inherit = root.get("inherit").getAsJsonArray();
      for (JsonElement jsonElement : inherit) {
        if (jsonElement.isJsonNull()) {
          continue;
        }
        dungeonSettings.getInherit().add(new SettingIdentifier(jsonElement.getAsString()));
      }
    }
  }

  private static void parseLootTables(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (root.has("loot_tables")) {
      throw new DungeonSettingParseException("Please update field \"loot_tables\" to \"lootTables\" to conform to JSON standards.");
    }
    if (root.has("lootTables")) {
      JsonArray lootTables = root.get("lootTables").getAsJsonArray();
      for (JsonElement jsonElement : lootTables) {
        if (jsonElement.isJsonNull()) {
          continue;
        }
        dungeonSettings.getLootTables().add(new LootTableRule(jsonElement.getAsJsonObject()));
      }
    }
  }

  private static void parseRoomCount(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("num_rooms")) {
      throw new DungeonSettingParseException("Please update field \"num_rooms\" to \"numRooms\" to conform to JSON standards.");
    }
    if (root.has("numRooms")) {
      JsonArray arr = root.get("numRooms").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement jsonElement = arr.get(i);
        if (jsonElement.isJsonNull()) {
          continue;
        }
        LevelSettings setting = dungeonSettings.getLevelSettings().get(i);
        setting.setNumRooms(jsonElement.getAsInt());
      }
    }
  }

  private static void parseScatter(JsonObject root, DungeonSettings dungeonSettings) {
    if (root.has("scatter")) {
      JsonArray arr = root.get("scatter").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement jsonElement = arr.get(i);
        if (jsonElement.isJsonNull()) {
          continue;
        }
        LevelSettings setting = dungeonSettings.getLevelSettings().get(i);
        setting.setScatter(jsonElement.getAsInt());
      }
    }
  }

  private static void parseLayouts(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("layouts")) {
      return;
    }
    JsonArray layouts = root.get("layouts").getAsJsonArray();
    for (JsonElement jsonElement : layouts) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      JsonObject layout = jsonElement.getAsJsonObject();
      if (layout.has("level")) {
        List<Integer> levels = LevelsParser.parseLevelsOrDefault(layout, ALL_LEVELS);
        for (Integer level : levels) {
          if (dungeonSettings.getLevelSettings().containsKey(level)) {
            LevelSettings setting = dungeonSettings.getLevelSettings().get(level);
            setting.setGenerator(LayoutGenerator.Type.valueOf(layout.get("type").getAsString().toUpperCase()));
          }
        }
      }
    }
  }

  private static void parseRooms(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("rooms")) {
      return;
    }
    JsonArray roomArray = root.get("rooms").getAsJsonArray();

    // TODO:
    // Step 1. Create a SecretSettings
    // Step 2. make secretsFactory.add(SecretSettings)
    // Step 3. Split this loop
    //
    // parseRoomSettings(roomArray);

    for (int floorLevel : dungeonSettings.getLevelSettings().keySet()) {

      RoomsSetting roomsSetting = new RoomsSetting();
      SecretsSetting secretsSetting = new SecretsSetting();
      for (JsonElement roomSettingElement : roomArray) {
        if (roomSettingElement.isJsonNull()) {
          continue;
        }
        JsonObject roomSettingJson = roomSettingElement.getAsJsonObject();
        RoomSetting roomSetting = RoomSettingParser.parse(roomSettingJson);
        if (roomSetting.isOnFloorLevel(floorLevel)) {
          if (roomSetting.isSecret()) {
            secretsSetting.add(roomSetting);
          } else {
            roomsSetting.add(roomSetting);
          }
        }
      }

      LevelSettings level = dungeonSettings.getLevelSettings().get(floorLevel);
      level.setRooms(roomsSetting);
      level.setSecrets(secretsSetting);
    }
  }

  private static void parseThemes(JsonObject rootJsonObject, DungeonSettings dungeonSettings) throws DungeonSettingParseException {
    if (!rootJsonObject.has("themes")) {
      return;
    }
    JsonElement themesJsonElement = rootJsonObject.get("themes");
    if (themesJsonElement.isJsonObject()) {
      throw new DungeonSettingParseException("Expected field 'themes' to be list of themes but instead found a single object.");
    }
    if (!themesJsonElement.isJsonArray()) {
      throw new DungeonSettingParseException("Expected field 'themes' to be list of themes but it wasn't.");
    }
    JsonArray themesJsonArray = themesJsonElement.getAsJsonArray();
    for (JsonElement themeJsonElement : themesJsonArray) {
      if (themeJsonElement.isJsonNull()) {
        continue;
      }
      JsonObject themeJsonObject = themeJsonElement.getAsJsonObject();
      List<Integer> levels = LevelsParser.parseLevelsOrDefault(themeJsonObject, ALL_LEVELS);
      if (levels == null) {
        continue;
      }

      for (int level : levels) {
        if (!dungeonSettings.getLevelSettings().containsKey(level)) {
          continue;
        }
        LevelSettings settings = dungeonSettings.getLevelSettings().get(level);
        Theme theme = ThemeParser.parse(themeJsonObject);
        settings.setTheme(theme);
      }
    }
  }

  private static void parseSegments(JsonObject dungeonSettingsJson, DungeonSettings dungeonSettings) {
    if (dungeonSettingsJson.has("segments")) {
      JsonElement segmentsElement = dungeonSettingsJson.get("segments");
      SegmentsParser.parseSegments(segmentsElement)
          .forEach((level, segments) ->
              dungeonSettings.getLevelSettings(level).getSegments().add(segments));
    }
  }

  private static void parseSpawners(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (!root.has("spawners")) {
      return;
    }
    JsonArray spawnersJson = root.get("spawners").getAsJsonArray();
    for (JsonElement spawnerJsonElement : spawnersJson) {
      JsonObject spawnerJson = spawnerJsonElement.getAsJsonObject();
      List<Integer> levels = LevelsParser.parseLevelsOrDefault(spawnerJson, ALL_LEVELS);
      Spawner spawner = SpawnerSettings.parseSpawnPotentials(spawnerJson);
      int weight = SpawnerSettings.parseWeight(spawnerJson);
      levels.stream()
          .mapToInt(level -> level)
          .filter(level -> dungeonSettings.getLevelSettings().containsKey(level))
          .forEach(level -> dungeonSettings.getLevelSettings().get(level).getSpawnerSettings().add(spawner, weight));
    }
  }

  private static void parseFilters(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("filters")) {
      return;
    }
    JsonArray filtersArray = root.get("filters").getAsJsonArray();
    for (JsonElement filterElement : filtersArray) {
      JsonObject filterObject = filterElement.getAsJsonObject();
      List<Integer> levels = LevelsParser.parseLevelsOrDefault(filterObject, ALL_LEVELS);
      for (int level : levels) {
        if (dungeonSettings.getLevelSettings().containsKey(level)) {
          String name = filterObject.get("name").getAsString();
          Filter type = Filter.valueOf(name.toUpperCase());
          dungeonSettings.getLevelSettings().get(level).addFilter(type);
        }
      }
    }
  }

  // todo: See above
  private static List<RoomSetting> parseRoomSettings(JsonArray roomArray) {
    List<RoomSetting> roomSettings = Lists.newArrayList();
    for (JsonElement roomSettingElement : roomArray) {
      if (roomSettingElement.isJsonNull()) {
        continue;
      }
      JsonObject roomSettingJson = roomSettingElement.getAsJsonObject();
      roomSettings.add(RoomSettingParser.parse(roomSettingJson));
    }
    return roomSettings;
  }
}
