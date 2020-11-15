package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.base.RoomType;

import static greymerk.roguelike.dungeon.settings.level.LevelsParser.parseLevelsIfPresent;

public class RoomSettingParser {

  public static final String TYPE_KEY = "type";
  public static final String ROOM_FREQUENCY = "frequency";
  public static final String COUNT_KEY = "count";
  public static final String WEIGHT_KEY = "weight";
  public static final String SPAWNER_KEY = "spawnerId";
  public static final String TREASURE_TYPE_KEY = "treasureType";

  public static RoomSetting parse(JsonObject roomSettingJson) {
    return new RoomSetting(
        parseType(roomSettingJson),
        parseSpawnerId(roomSettingJson),
        parseRoomFrequency(roomSettingJson),
        parseWeight(roomSettingJson),
        parseCount(roomSettingJson),
        parseLevelsIfPresent(roomSettingJson),
        parseTreasureType(roomSettingJson)
    );
  }

  private static RoomType parseType(JsonObject roomSettingJson) {
    if (!roomSettingJson.has(TYPE_KEY)) {
      throw new RuntimeException(String.format("Room setting is missing a value for key '%s' which is required", TYPE_KEY));
    }
    return RoomType.valueOf(roomSettingJson.get(TYPE_KEY).getAsString().toUpperCase());
  }

  private static Frequency parseRoomFrequency(JsonObject roomSettingJson) {
    return roomSettingJson.has(ROOM_FREQUENCY)
        ? Frequency.valueOf(roomSettingJson.get(ROOM_FREQUENCY).getAsString().toUpperCase())
        : Frequency.SINGLE;
  }

  private static int parseCount(JsonObject roomSettingJson) {
    return roomSettingJson.has(COUNT_KEY)
        ? roomSettingJson.get(COUNT_KEY).getAsInt()
        : 1;
  }

  private static int parseWeight(JsonObject roomSettingJson) {
    return roomSettingJson.has(WEIGHT_KEY)
        ? roomSettingJson.get(WEIGHT_KEY).getAsInt()
        : 1;
  }

  private static String parseSpawnerId(JsonObject roomSettingJson) {
    return roomSettingJson.has(SPAWNER_KEY)
        ? roomSettingJson.get(SPAWNER_KEY).getAsString()
        : null;
  }

  private static String parseTreasureType(JsonObject roomSettingJson) {
    if (!roomSettingJson.has(TREASURE_TYPE_KEY)) {
      return null;
    }
    JsonElement treasureTypeValue = roomSettingJson.get(TREASURE_TYPE_KEY);
    if (treasureTypeValue.isJsonNull()) {
      return null;
    }
    return treasureTypeValue.getAsString();
  }
}
