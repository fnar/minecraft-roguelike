package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.onLevel;

public class SingleUseLootRule implements LootRule {

  private final int level;
  private WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>();
  private final int amount;

  public SingleUseLootRule(WeightedRandomizer<ItemStack> item, int level, int amount) {
    this.items = item;
    this.level = level;
    this.amount = amount;
  }

  public SingleUseLootRule(IWeighted<ItemStack> item, int level, int amount) {
    this.items.add(item);
    this.level = level;
    this.amount = amount;
  }

  public void process(TreasureManager treasureManager) {
    if (isEmpty()) {
      return;
    }
    treasureManager.addItem(onLevel(level), items, amount);
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

}
