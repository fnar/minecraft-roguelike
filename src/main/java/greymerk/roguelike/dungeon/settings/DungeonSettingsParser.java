package greymerk.roguelike.dungeon.settings;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.rooms.RoomSettingParser;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.level.LevelsParser;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.ThemeParser;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.worldgen.filter.Filter;

public class DungeonSettingsParser {

  public static DungeonSettings parseJson(String content) throws Exception {
    try {
      return parseDungeonSettings((JsonObject) new JsonParser().parse(content));
    } catch (JsonSyntaxException e) {
      Throwable cause = e.getCause();
      throw new Exception(cause.getMessage());
    } catch (Exception e) {
      throw new Exception("An unknown error occurred while parsing json");
    }
  }

  public static DungeonSettings parseDungeonSettings(JsonObject root) throws Exception {
    DungeonSettings dungeonSettings = new DungeonSettings();
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

    if (root.has("exclusive")) {
      dungeonSettings.setExclusive(root.get("exclusive").getAsBoolean());
    }
    if (root.has("criteria")) {
      dungeonSettings.setSpawnCriteria(new SpawnCriteria(root.get("criteria").getAsJsonObject()));
    }
    if (root.has("tower")) {
      dungeonSettings.setTowerSettings(new TowerSettings(root.get("tower")));
    }
    if (root.has("loot_rules")) {
      dungeonSettings.setLootRules(new LootRuleManager(root.get("loot_rules")));
    }

    if (root.has("overrides")) {
      JsonArray overrides = root.get("overrides").getAsJsonArray();
      for (JsonElement e : overrides) {
        String type = e.getAsString();
        dungeonSettings.getOverrides().add(SettingsType.valueOf(type));
      }
    }

    if (root.has("inherit")) {
      JsonArray inherit = root.get("inherit").getAsJsonArray();
      for (JsonElement e : inherit) {
        dungeonSettings.getInherit().add(new SettingIdentifier(e.getAsString()));
      }
    }

    // set up level settings objects
    for (int i = 0; i < DungeonSettings.getMaxNumLevels(); ++i) {
      LevelSettings setting = new LevelSettings();
      dungeonSettings.getLevels().put(i, setting);
    }

    if (root.has("loot_tables")) {
      JsonArray arr = root.get("loot_tables").getAsJsonArray();
      for (JsonElement e : arr) {
        dungeonSettings.getLootTables().add(new LootTableRule(e.getAsJsonObject()));
      }
    }

    if (root.has("num_rooms")) {
      JsonArray arr = root.get("num_rooms").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = dungeonSettings.getLevels().get(i);
        setting.setNumRooms(e.getAsInt());
      }
    }

    if (root.has("scatter")) {
      JsonArray arr = root.get("scatter").getAsJsonArray();
      for (int i = 0; i < arr.size(); ++i) {
        JsonElement e = arr.get(i);
        LevelSettings setting = dungeonSettings.getLevels().get(i);
        setting.setScatter(e.getAsInt());
      }
    }
    parseLayouts(root, dungeonSettings);
    parseSpawners(root, dungeonSettings);
    parseRooms(root, dungeonSettings);
    parseThemes(root, dungeonSettings);
    parseSegments(root, dungeonSettings);
    parseFilters(root, dungeonSettings);
    return dungeonSettings;
  }

  private static void parseLayouts(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("layouts")) {
      return;
    }
    JsonArray layouts = root.get("layouts").getAsJsonArray();
    for (JsonElement e : layouts) {
      JsonObject layout = e.getAsJsonObject();
      if (layout.has("level")) {
        List<Integer> levels = LevelsParser.parseLevelsIfPresent(layout);
        for (Integer level : levels) {
          if (dungeonSettings.getLevels().containsKey(level)) {
            LevelSettings setting = dungeonSettings.getLevels().get(level);
            setting.setGenerator(LevelGenerator.valueOf(layout.get("type").getAsString().toUpperCase()));
          }
        }
      }
    }
  }

  private static void parseRooms(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
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

    for (int floorLevel : dungeonSettings.getLevels().keySet()) {

      RoomsSetting roomsSetting = new RoomsSetting();
      SecretFactory secretFactory = new SecretFactory();
      for (JsonElement roomSettingElement : roomArray) {
        JsonObject roomSettingJson = roomSettingElement.getAsJsonObject();
        RoomSetting roomSetting = RoomSettingParser.parse(roomSettingJson);
        if (roomSetting.isOnFloorLevel(floorLevel)) {
          if (roomSetting.isSecret()) {
            secretFactory.add(roomSetting);
          } else {
            roomsSetting.add(roomSetting);
          }
        }
      }

      LevelSettings level = dungeonSettings.getLevels().get(floorLevel);
      level.setRooms(roomsSetting);
      level.setSecrets(secretFactory);
    }
  }

  private static void parseThemes(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (!root.has("themes")) {
      return;
    }
    JsonArray arr = root.get("themes").getAsJsonArray();
    for (JsonElement e : arr) {
      JsonObject entry = e.getAsJsonObject();
      List<Integer> lvls = LevelsParser.parseLevelsIfPresent(entry);
      if (lvls == null) {
        continue;
      }

      for (int i : lvls) {
        if (dungeonSettings.getLevels().containsKey(i)) {
          LevelSettings settings = dungeonSettings.getLevels().get(i);
          ITheme theme = ThemeParser.parse(entry);
          settings.setTheme(theme);
        }
      }
    }
  }

  private static void parseSegments(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("segments")) {
      return;
    }
    JsonArray arr = root.get("segments").getAsJsonArray();
    for (int lvl : dungeonSettings.getLevels().keySet()) {
      boolean hasEntry = false;
      SegmentGenerator segments = new SegmentGenerator();
      for (JsonElement e : arr) {
        JsonObject entry = e.getAsJsonObject();
        List<Integer> lvls = LevelsParser.parseLevelsIfPresent(entry);
        if (!lvls.contains(lvl)) {
          continue;
        }

        hasEntry = true;
        segments.add(entry);
      }

      if (hasEntry) {
        dungeonSettings.getLevels().get(lvl).setSegments(segments);
      }
    }
  }

  private static void parseSpawners(JsonObject root, DungeonSettings dungeonSettings) throws Exception {
    if (!root.has("spawners")) {
      return;
    }
    JsonArray arr = root.get("spawners").getAsJsonArray();
    for (JsonElement e : arr) {
      JsonObject entry = e.getAsJsonObject();
      List<Integer> lvls = LevelsParser.parseLevelsIfPresent(entry);
      for (int i : lvls) {
        if (dungeonSettings.getLevels().containsKey(i)) {
          dungeonSettings.getLevels().get(i).getSpawners().parse(entry);
        }
      }
    }
  }

  private static void parseFilters(JsonObject root, DungeonSettings dungeonSettings) {
    if (!root.has("filters")) {
      return;
    }
    JsonArray arr = root.get("filters").getAsJsonArray();
    for (JsonElement e : arr) {
      JsonObject entry = e.getAsJsonObject();
      List<Integer> lvls = LevelsParser.parseLevelsIfPresent(entry);
      for (int i : lvls) {
        if (dungeonSettings.getLevels().containsKey(i)) {
          String name = entry.get("name").getAsString();
          Filter type = Filter.valueOf(name.toUpperCase());
          LevelSettings level = dungeonSettings.getLevels().get(i);
          level.addFilter(type);
        }
      }
    }
  }

  // todo: See above
  private static List<RoomSetting> parseRoomSettings(JsonArray roomArray) throws Exception {
    List<RoomSetting> roomSettings = Lists.newArrayList();
    for (JsonElement roomSettingElement : roomArray) {
      JsonObject roomSettingJson = roomSettingElement.getAsJsonObject();
      roomSettings.add(RoomSettingParser.parse(roomSettingJson));
    }
    return roomSettings;
  }
}
