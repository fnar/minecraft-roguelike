package com.github.fnar.minecraft.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

public class TagParser {

  public Tag parse(JsonElement jsonElement) {
    if (jsonElement.isJsonNull()) {
      return null;
    }
    if (jsonElement.isJsonArray()) {
      return parse(jsonElement.getAsJsonArray());
    }
    if (jsonElement.isJsonObject()) {
      return parse(jsonElement.getAsJsonObject());
    }
    if (jsonElement.isJsonPrimitive()) {
      return new StringTag(jsonElement.getAsString());
    }
    return null;
  }

  public CompoundTag parse(JsonObject jsonObject) {
    CompoundTag compoundTag = new CompoundTag();
    Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
    for (Map.Entry<String, JsonElement> entry : entries) {
      String key = entry.getKey();
      JsonElement value = entry.getValue();
      compoundTag.withTag(key, parse(value));
    }

    return compoundTag;
  }

  public ListTag parse(JsonArray array) {
    ListTag listTag = new ListTag();
    for (int i = 0; i < array.size(); i++) {
      listTag.withTag(parse(array.get(i)));
    }

    return listTag;
  }
}
