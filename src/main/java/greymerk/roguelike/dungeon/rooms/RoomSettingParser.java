package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;

import static greymerk.roguelike.dungeon.base.DungeonRoom.valueOf;
import static greymerk.roguelike.dungeon.settings.level.LevelsParser.parseLevelsIfPresent;

public class RoomSettingParser {

  public static final String SPAWNERS_KEY = "spawners";
  public static final String COUNT_KEY = "count";
  public static final String WEIGHT_KEY = "weight";

  public static RoomSetting parse(JsonObject roomSettingJson) throws Exception {
    return new RoomSetting(
        parseDungeonRoom(roomSettingJson),
        parseSpawners(roomSettingJson),
        parseRoomFrequency(roomSettingJson),
        parseWeight(roomSettingJson),
        parseCount(roomSettingJson),
        parseLevelsIfPresent(roomSettingJson));
  }

  private static DungeonRoom parseDungeonRoom(JsonObject entry) throws Exception {
    try {
      return valueOf(getName(entry));
    } catch (IllegalArgumentException e) {
      throw new Exception("No such dungeon: " + getName(entry));
    }
  }

  private static Spawner parseSpawners(JsonObject entry) throws Exception {
    if (!entry.has(SPAWNERS_KEY)) {
      return null;
    }
    try {
      return Spawner.valueOf(entry.get(SPAWNERS_KEY).getAsString().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new Exception("No such spawner type: " + getName(entry));
    }
  }

  private static String getName(JsonObject entry) {
    return entry.get("name").getAsString();
  }

  private static int parseCount(JsonObject roomSettingJson) {
    return roomSettingJson.has(COUNT_KEY) ? roomSettingJson.get(COUNT_KEY).getAsInt() : 1;
  }

  private static int parseWeight(JsonObject entry) {
    return entry.has(WEIGHT_KEY) ? entry.get(WEIGHT_KEY).getAsInt() : 1;
  }

  private static String parseRoomFrequency(JsonObject entry) {
    return entry.get("type").getAsString().toLowerCase();
  }
}
