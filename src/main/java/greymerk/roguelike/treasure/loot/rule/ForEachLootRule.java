package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;

public class ForEachLootRule implements ILootRule {

  private final IWeighted<ItemStack> item;
  private final int level;
  private final int amount;

  public ForEachLootRule(IWeighted<ItemStack> item, int level, int amount) {
    this.item = item;
    this.level = level;
    this.amount = amount;
  }

  @Override
  public void process(TreasureManager treasureManager) {
    treasureManager.addItemToAll(level, item, amount);
  }
}
