package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.onLevel;

public class ForEachLootRule implements LootRule {

  private final int level;
  private WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>();
  private final int amount;

  public ForEachLootRule(WeightedRandomizer<ItemStack> items, int level, int amount) {
    this.items = items;
    this.level = level;
    this.amount = amount;
  }

  public ForEachLootRule(IWeighted<ItemStack> item, int level, int amount) {
    this.items.add(item);
    this.level = level;
    this.amount = amount;
  }

  @Override
  public void process(TreasureManager treasureManager) {
    if (isEmpty()) {
      return;
    }
    treasureManager.addItemToAll(onLevel(level), items, amount);
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

}
