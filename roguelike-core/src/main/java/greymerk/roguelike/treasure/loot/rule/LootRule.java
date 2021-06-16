package greymerk.roguelike.treasure.loot.rule;

import greymerk.roguelike.treasure.TreasureManager;

public interface LootRule {

  void process(TreasureManager treasureManager);

  boolean isEmpty();

}
