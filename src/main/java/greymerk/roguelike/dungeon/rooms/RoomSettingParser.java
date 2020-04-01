package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.base.DungeonRoom;

import static greymerk.roguelike.dungeon.base.DungeonRoom.valueOf;

public class RoomSettingParser {

  public static RoomSetting parse(JsonObject entry) throws Exception {
    return new RoomSetting(
        getDungeonRoom(entry),
        getRoomFrequency(entry),
        getWeight(entry));
  }

  private static DungeonRoom getDungeonRoom(JsonObject entry) throws Exception {
    try {
      return valueOf(getName(entry));
    } catch (IllegalArgumentException e) {
      throw new Exception("No such dungeon: " + getName(entry));
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
