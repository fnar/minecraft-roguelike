package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.worldgen.spawners.Spawner;

import static greymerk.roguelike.dungeon.base.DungeonRoom.valueOf;
import static greymerk.roguelike.dungeon.settings.level.LevelsParser.parseLevelsIfPresent;
import static java.lang.String.format;

public class RoomSettingParser {

  public static final String NAME_KEY = "name";
  public static final String ROOM_FREQUENCY = "type";
  public static final String COUNT_KEY = "count";
  public static final String WEIGHT_KEY = "weight";
  public static final String SPAWNERS_KEY = "spawners";

  public static RoomSetting parse(JsonObject roomSettingJson) throws Exception {
    return new RoomSetting(
        parseName(roomSettingJson),
        parseSpawners(roomSettingJson),
        parseRoomFrequency(roomSettingJson),
        parseWeight(roomSettingJson),
        parseCount(roomSettingJson),
        parseLevelsIfPresent(roomSettingJson));
  }

  private static DungeonRoom parseName(JsonObject entry) throws Exception {
    String name = entry.has(NAME_KEY)
        ? entry.get(NAME_KEY).getAsString().toUpperCase()
        : "NAME MISSING";
    try {
      return valueOf(name);
    } catch (IllegalArgumentException e) {
      throw new Exception(format("No such room with name %s", name));
    }
  }

  private static String parseRoomFrequency(JsonObject entry) {
    return entry.get(ROOM_FREQUENCY).getAsString().toLowerCase();
  }

  private static int parseCount(JsonObject roomSettingJson) {
    return roomSettingJson.has(COUNT_KEY)
        ? roomSettingJson.get(COUNT_KEY).getAsInt()
        : 1;
  }

  private static int parseWeight(JsonObject entry) {
    return entry.has(WEIGHT_KEY)
        ? entry.get(WEIGHT_KEY).getAsInt()
        : 1;
  }

  private static Spawner parseSpawners(JsonObject entry) throws Exception {
    if (!entry.has(SPAWNERS_KEY)) {
      return null;
    }
    String spawner = entry.get(SPAWNERS_KEY).getAsString().toUpperCase();
    try {
      return Spawner.valueOf(spawner);
    } catch (IllegalArgumentException e) {
      throw new Exception(format("No such spawner type %s for room of type %s", spawner, parseName(entry)));
    }
  }
}
