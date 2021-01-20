package greymerk.roguelike.dungeon.settings.level;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class LevelsParser {

  public static List<Integer> parseLevelsOrDefault(JsonObject parentObject, List<Integer> defaultValue) {
    if (!parentObject.has("level")) {
      return defaultValue;
    }
    JsonElement levelElement = parentObject.get("level");
    if (!levelElement.isJsonArray()) {
      return Lists.newArrayList(levelElement.getAsInt());
    }
    JsonArray levelsArray = levelElement.getAsJsonArray();
    List<Integer> levels = new ArrayList<>();
    levelsArray.forEach(level -> levels.add(level.getAsInt()));
    return levels;
  }
}
