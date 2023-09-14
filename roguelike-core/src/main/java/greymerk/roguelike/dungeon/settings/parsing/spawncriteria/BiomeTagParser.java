package greymerk.roguelike.dungeon.settings.parsing.spawncriteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.github.fnar.minecraft.world.BiomeTag;

import java.util.HashSet;
import java.util.Set;

public class BiomeTagParser {

  public static Set<BiomeTag> parse(JsonObject data) {
    Set<BiomeTag> biomeTags = new HashSet<>();
    if (data.has("biomeTypes")) {
      for (JsonElement biomeTagsJson : data.get("biomeTypes").getAsJsonArray()) {
        if (!biomeTagsJson.isJsonNull()) {
          String biomeTagString = biomeTagsJson.getAsString().toUpperCase();
          BiomeTag biomeTag = BiomeTag.valueOf(biomeTagString);
          biomeTags.add(biomeTag);
        }
      }
    }
    return biomeTags;
  }

}
