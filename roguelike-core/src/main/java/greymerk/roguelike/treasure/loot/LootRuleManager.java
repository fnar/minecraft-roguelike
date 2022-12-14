package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.rule.LootRule;

public class LootRuleManager {

  private final List<LootRule> rules = new ArrayList<>();

  public void merge(LootRuleManager other) {
    if (other == null) {
      return;
    }
    addAll(filterNonEmptyLootRules(other.rules));
  }

  public void add(LootRule toAdd) {
    this.rules.add(toAdd);
  }

  public void addAll(List<LootRule> rules) {
    this.rules.addAll(filterNonEmptyLootRules(rules));
  }

  private List<LootRule> filterNonEmptyLootRules(List<LootRule> rules) {
    Predicate<LootRule> isEmpty = LootRule::isEmpty;
    Predicate<LootRule> isNotEmpty = isEmpty.negate();
    return rules.stream().filter(isNotEmpty).collect(Collectors.toList());
  }

  public void process(TreasureManager treasure) {
    this.rules.forEach(rule -> {
      try {
        rule.process(treasure);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public List<LootRule> getRules() {
    return rules;
  }

  @Override
  public String toString() {
    return Integer.toString(this.rules.size());
  }
}
