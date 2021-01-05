package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;

import static greymerk.roguelike.treasure.TreasureManager.onLevel;

public class ForEachLootRule implements LootRule {

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
    treasureManager.addItemToAll(onLevel(level), item, amount);
  }
}
