package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public class LootRuleManager {

  private List<LootRule> rules = new ArrayList<>();

  public void add(Treasure treasure, ItemStack itemStack) {
    WeightedChoice<ItemStack> weightedChoice = new WeightedChoice<>(itemStack, 1);
    add(treasure, weightedChoice, 0, true, 1);
  }

  public void add(Treasure type, IWeighted<ItemStack> item, int level, boolean toEach, int amount) {
    add(new LootRule(type, item, level, toEach, amount));
  }

  public void merge(LootRuleManager other) {
    if (other == null) {
      return;
    }
    addAll(other.rules);
  }

  public void add(LootRule toAdd) {
    this.rules.add(toAdd);
  }

  public void addAll(List<LootRule> rules) {
    this.rules.addAll(rules);
  }

  public void process(TreasureManager treasure) {
    this.rules.forEach(rule -> rule.process(treasure));
  }

  @Override
  public String toString() {
    return Integer.toString(this.rules.size());
  }
}
