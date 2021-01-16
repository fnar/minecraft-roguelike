package greymerk.roguelike.treasure.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.LootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedSingleUseLootRule;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootRulesParser {

  public static final List<Integer> ALL_LEVELS = Collections.unmodifiableList(Lists.newArrayList(0, 1, 2, 3, 4));

  public List<LootRule> parseLootRules(JsonElement jsonElement) throws Exception {

    List<LootRule> lootRules = new ArrayList<>();

    for (JsonElement ruleElement : jsonElement.getAsJsonArray()) {
      if (ruleElement.isJsonNull()) {
        continue;
      }

      JsonObject rule = ruleElement.getAsJsonObject();

      if (!rule.has("loot")) {
        continue;
      }

      Optional<ChestType> chestType = rule.has("type")
          ? Optional.of(new ChestType(rule.get("type").getAsString()))
          : rule.has("chestType")
              ? Optional.of(new ChestType(rule.get("chestType").getAsString()))
              : Optional.empty();

      JsonArray data = rule.get("loot").getAsJsonArray();
      WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(1);
      for (JsonElement item : data) {
        if (item.isJsonNull()) {
          continue;
        }
        items.add(parseProvider(item.getAsJsonObject()));
      }

      List<Integer> levels = parseLevels(rule);

      boolean each = rule.get("each").getAsBoolean();
      int amount = rule.get("quantity").getAsInt();

      for (int level : levels) {
        lootRules.add(newLootRule(items, amount, level, each, chestType));
      }
    }
    return lootRules;
  }

  public List<Integer> parseLevels(JsonObject rule) {

    if (!rule.has("level")) {
      return ALL_LEVELS;
    }

    JsonElement levelElement = rule.get("level");

    if (levelElement.isJsonArray()) {
      JsonArray levelArray = levelElement.getAsJsonArray();
      List<Integer> levels = new ArrayList<>();
      levelArray.forEach(level -> levels.add(level.getAsInt()));
      return levels;
    }

    List<Integer> levels = new ArrayList<>();
    levels.add(rule.get("level").getAsInt());
    return levels;

  }

  private LootRule newLootRule(WeightedRandomizer<ItemStack> items, int amount, int level, boolean each, Optional<ChestType> chestType) {
    if (each && chestType.isPresent()) {
      return new TypedForEachLootRule(chestType.get(), items, level, amount);
    } else if (each) {
      return new ForEachLootRule(items, level, amount);
    } else if (chestType.isPresent()) {
      return new TypedSingleUseLootRule(chestType.get(), items, level, amount);
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
