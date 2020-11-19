package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.rule.LootRule;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootRulesParser {

  public List<LootRule> parseLootRules(JsonElement jsonElement) throws Exception {
    List<LootRule> lootRules = new ArrayList<>();
    for (JsonElement ruleElement : jsonElement.getAsJsonArray()) {
      if (ruleElement.isJsonNull()) {
        continue;
      }

      JsonObject rule = ruleElement.getAsJsonObject();

      Treasure type = rule.has("type") ? Treasure.valueOf(rule.get("type").getAsString()) : null;

      if (!rule.has("loot")) {
        continue;
      }
      JsonArray data = rule.get("loot").getAsJsonArray();
      WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(1);
      for (JsonElement item : data) {
        if (item.isJsonNull()) {
          continue;
        }
        items.add(parseProvider(item.getAsJsonObject()));
      }

      List<Integer> levels = new ArrayList<>();
      JsonElement levelElement = rule.get("level");
      if (levelElement.isJsonArray()) {
        JsonArray levelArray = levelElement.getAsJsonArray();
        for (JsonElement lvl : levelArray) {
          if (lvl.isJsonNull()) {
            continue;
          }
          levels.add(lvl.getAsInt());
        }
      } else {
        levels.add(rule.get("level").getAsInt());
      }

      boolean each = rule.get("each").getAsBoolean();
      int amount = rule.get("quantity").getAsInt();

      for (int level : levels) {
        lootRules.add(new LootRule(type, items, level, each, amount));
      }
    }
    return lootRules;
  }

  private IWeighted<ItemStack> parseProvider(JsonObject lootItem) throws Exception {

    int weight = lootItem.has("weight") ? lootItem.get("weight").getAsInt() : 1;

    if (lootItem.get("data").isJsonObject()) {
      JsonObject data = lootItem.get("data").getAsJsonObject();
      return Loot.get(data, weight);
    }

    JsonArray data = lootItem.get("data").getAsJsonArray();
    WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(weight);
    for (JsonElement jsonElement : data) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      items.add(parseProvider(jsonElement.getAsJsonObject()));
    }

    return items;
  }
}
