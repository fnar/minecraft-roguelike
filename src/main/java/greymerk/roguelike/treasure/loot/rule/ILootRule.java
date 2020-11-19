package greymerk.roguelike.treasure.loot.rule;

import greymerk.roguelike.treasure.TreasureManager;

public interface ILootRule {
  void process(TreasureManager treasureManager);

}
