package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;

import static greymerk.roguelike.dungeon.base.DungeonRoom.valueOf;
import static greymerk.roguelike.dungeon.settings.level.LevelsParser.parseLevelsIfPresent;

public class RoomSettingParser {

  public static final String SPAWNERS_KEY = "spawners";

  public static RoomSetting parse(JsonObject roomSettingJson) throws Exception {
    return new RoomSetting(
        getDungeonRoom(roomSettingJson),
        getSpawner(roomSettingJson),
        getRoomFrequency(roomSettingJson),
        getWeight(roomSettingJson),
        parseLevelsIfPresent(roomSettingJson));
  }

  private static DungeonRoom getDungeonRoom(JsonObject entry) throws Exception {
    try {
      return valueOf(getName(entry));
    } catch (IllegalArgumentException e) {
      throw new Exception("No such dungeon: " + getName(entry));
    }
  }

  private static Spawner getSpawner(JsonObject entry) throws Exception {
    if (!entry.has(SPAWNERS_KEY)) {
      return null;
    }
    try {
      return Spawner.valueOf(entry.get(SPAWNERS_KEY).getAsString().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new Exception("No such spawner type: " + getName(entry));
    }
  }

  private static int getWeight(JsonObject entry) {
    return entry.has("weight") ? entry.get("weight").getAsInt() : 1;
  }

  private static String getName(JsonObject entry) {
    return entry.get("name").getAsString();
  }

  private static String getRoomFrequency(JsonObject entry) {
    return entry.get("type").getAsString().toLowerCase();
  }
}
