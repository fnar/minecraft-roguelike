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
import java.util.stream.Collectors;

import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.LootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedSingleUseLootRule;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootRulesParser {

  public static final List<Integer> ALL_LEVELS = Collections.unmodifiableList(Lists.newArrayList(0, 1, 2, 3, 4));

  public List<LootRule> parseLootRules(JsonElement lootRulesElement) throws Exception {
    List<LootRule> lootRules = new ArrayList<>();
    for (JsonElement lootRuleElement : lootRulesElement.getAsJsonArray()) {
      lootRules.addAll(parseLootRule(lootRuleElement));
    }
    return lootRules;
  }

  public List<LootRule> parseLootRule(JsonElement lootRuleElement) throws Exception {
    if (!lootRuleElement.isJsonNull()) {
      JsonObject ruleObject = lootRuleElement.getAsJsonObject();
      if (ruleObject.has("loot")) {
        return parseLootRule(ruleObject);
      }
    }
    return new ArrayList<>();
  }

  public List<LootRule> parseLootRule(JsonObject ruleObject) throws Exception {
    JsonArray lootArray = ruleObject.get("loot").getAsJsonArray();

    List<Integer> levels = parseLevels(ruleObject);
    WeightedRandomizer<ItemStack> items = parseLootItems(lootArray);
    int amount = ruleObject.get("quantity").getAsInt();
    boolean each = ruleObject.get("each").getAsBoolean();
    Optional<ChestType> chestType = parseChestType(ruleObject);

    return levels.stream()
        .map(level -> newLootRule(items, amount, level, each, chestType))
        .collect(Collectors.toList());
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

  private LootRule newLootRule(WeightedRandomizer<ItemStack> items, int amount, int level, boolean isEach, Optional<ChestType> chestType) {
    return chestType.map(type -> isEach
        ? new TypedForEachLootRule(type, items, level, amount)
        : new TypedSingleUseLootRule(type, items, level, amount))
        .orElseGet(() -> isEach
            ? new ForEachLootRule(items, level, amount)
            : new SingleUseLootRule(items, level, amount));
  }

  private IWeighted<ItemStack> parseLootItemProvider(JsonObject lootItem) throws Exception {

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
      items.add(parseLootItemProvider(jsonElement.getAsJsonObject()));
    }

    return items;
  }

  public Optional<ChestType> parseChestType(JsonObject rule) {
    return rule.has("type")
        ? Optional.of(new ChestType(rule.get("type").getAsString()))
        : rule.has("chestType")
            ? Optional.of(new ChestType(rule.get("chestType").getAsString()))
            : Optional.empty();
  }

  public WeightedRandomizer<ItemStack> parseLootItems(JsonArray data) throws Exception {
    WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(1);
    for (JsonElement item : data) {
      if (item.isJsonNull()) {
        continue;
      }
      IWeighted<ItemStack> lootItemProvider = parseLootItemProvider(item.getAsJsonObject());
      items.add(lootItemProvider);
    }
    return items;
  }
}
