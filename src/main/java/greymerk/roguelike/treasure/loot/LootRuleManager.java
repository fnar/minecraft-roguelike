package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.rule.ILootRule;

public class LootRuleManager {

  private List<ILootRule> rules = new ArrayList<>();

  public void merge(LootRuleManager other) {
    if (other == null) {
      return;
    }
    addAll(other.rules);
  }

  public void add(ILootRule toAdd) {
    this.rules.add(toAdd);
  }

  public void addAll(List<ILootRule> rules) {
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
