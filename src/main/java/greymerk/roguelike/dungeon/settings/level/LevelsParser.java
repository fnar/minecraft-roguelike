package greymerk.roguelike.dungeon.settings.level;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class LevelsParser {

  public static List<Integer> parseLevelsIfPresent(JsonObject roomSettingJson) {
    return roomSettingJson.has("level")
        ? parseLevels(roomSettingJson)
        : null;
  }

  public static List<Integer> parseLevels(JsonObject roomSettingJson) {
    JsonElement levelJson = roomSettingJson.get("level");
    if (!levelJson.isJsonArray()) {
      return newArrayList(levelJson.getAsInt());
    }
    JsonArray levelsArray = levelJson.getAsJsonArray();
    List<Integer> levels = new ArrayList<>();
    for (JsonElement jsonElement : levelsArray) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      levels.add(jsonElement.getAsInt());
    }
    return levels;
  }
}
