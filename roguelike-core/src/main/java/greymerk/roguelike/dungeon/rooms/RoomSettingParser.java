package greymerk.roguelike.dungeon.rooms;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Optional;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.treasure.loot.ChestType;

import static greymerk.roguelike.dungeon.settings.DungeonSettingsParser.ALL_LEVELS;
import static greymerk.roguelike.dungeon.settings.level.LevelsParser.parseLevelsOrDefault;

public class RoomSettingParser {

  public static final String TYPE_KEY = "type";
  public static final String ROOM_FREQUENCY = "frequency";
  public static final String COUNT_KEY = "count";
  public static final String WEIGHT_KEY = "weight";
  public static final String SPAWNER_KEY = "spawnerId";
  public static final String CHEST_TYPE_KEY = "chestType";

  public static RoomSetting parse(JsonObject roomSettingJson) {
    return new RoomSetting(
        parseType(roomSettingJson),
        parseSpawnerId(roomSettingJson),
        parseRoomFrequency(roomSettingJson),
        parseWeight(roomSettingJson),
        parseCount(roomSettingJson),
        parseLevelsOrDefault(roomSettingJson, ALL_LEVELS),
        parseChestType(roomSettingJson)
    );
  }

  private static RoomType parseType(JsonObject roomSettingJson) {
    if (!roomSettingJson.has(TYPE_KEY)) {
      throw new RuntimeException(String.format("Room setting is missing a value for key '%s' which is required", TYPE_KEY));
    }
    return RoomType.fromString(roomSettingJson.get(TYPE_KEY).getAsString());
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

  private static Optional<ChestType> parseChestType(JsonObject roomSettingJson) {
    if (!roomSettingJson.has(CHEST_TYPE_KEY)) {
      return Optional.empty();
    }
    JsonElement treasureTypeValue = roomSettingJson.get(CHEST_TYPE_KEY);
    if (treasureTypeValue.isJsonNull()) {
      return Optional.empty();
    }
    return Optional.of(new ChestType(treasureTypeValue.getAsString()));
  }
}
