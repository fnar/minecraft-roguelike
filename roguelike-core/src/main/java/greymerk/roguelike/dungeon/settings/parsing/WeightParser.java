package greymerk.roguelike.dungeon.settings.parsing;

import com.google.gson.JsonObject;

public class WeightParser {

  public static int parse(JsonObject data) {
    return data.has("weight") ? data.get("weight").getAsInt() : 1;
  }

}
