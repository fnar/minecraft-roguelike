package greymerk.roguelike.dungeon.settings.parsing.spawncriteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Set;

public class DimensionParser {

  public static Set<Integer> parse(JsonObject data) {
    Set<Integer> dimensions = new HashSet<>();
    if (data.has("dimensions")) {
      for (JsonElement dimension : data.get("dimensions").getAsJsonArray()) {
        if (!dimension.isJsonNull()) {
          dimensions.add(dimension.getAsInt());
        }
      }
    }
    return dimensions;
  }

}
