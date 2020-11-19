package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;

public class SingleUseLootRule implements LootRule {

  int level;
  int amount;
  private final IWeighted<ItemStack> item;

  public SingleUseLootRule(IWeighted<ItemStack> item, int level, int amount) {
    this.item = item;
    this.level = level;
    this.amount = amount;
  }

  public void process(TreasureManager treasureManager) {
    treasureManager.addItem(level, item, amount);
  }
}
