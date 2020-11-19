package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.ILootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedSingleUseLootRule;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootRulesParser {

  public List<ILootRule> parseLootRules(JsonElement jsonElement) throws Exception {
    List<ILootRule> lootRules = new ArrayList<>();
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
        lootRules.add(newLootRule(type, items, each, amount, level));
      }
    }
    return lootRules;
  }

  private ILootRule newLootRule(Treasure type, WeightedRandomizer<ItemStack> items, boolean each, int amount, int level) {
    if (each && type != null) {
      return new TypedForEachLootRule(type, items, level, amount);
    } else if (each) {
      return new ForEachLootRule(items, level, amount);
    } else if (type != null) {
      return new TypedSingleUseLootRule(type, items, level, amount);
    } else {
      return new SingleUseLootRule(items, level, amount);
    }
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
