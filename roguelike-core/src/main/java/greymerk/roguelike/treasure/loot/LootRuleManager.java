package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.rule.LootRule;

public class LootRuleManager {

  private final List<LootRule> rules = new ArrayList<>();

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

  public List<LootRule> getRules() {
    return rules;
  }

  @Override
  public String toString() {
    return Integer.toString(this.rules.size());
  }
}
