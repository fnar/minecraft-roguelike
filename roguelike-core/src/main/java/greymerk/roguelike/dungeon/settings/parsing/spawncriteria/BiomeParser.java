package greymerk.roguelike.dungeon.settings.parsing.spawncriteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Set;

public class BiomeParser {

  public static Set<String> parse(JsonObject data) {
    Set<String> biomes = new HashSet<>();
    if (data.has("biomes")) {
      for (JsonElement biome : data.get("biomes").getAsJsonArray()) {
        if (!biome.isJsonNull()) {
          biomes.add(biome.getAsString());
        }
      }
    }
    return biomes;
  }

}
