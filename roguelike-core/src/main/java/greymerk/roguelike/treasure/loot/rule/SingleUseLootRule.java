package greymerk.roguelike.treasure.loot.rule;

import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.onLevelAndNotEmpty;

public class SingleUseLootRule implements LootRule {

  private final int level;
  private WeightedRandomizer<RldItemStack> items = new WeightedRandomizer<>();
  private final int amount;

  public SingleUseLootRule(WeightedRandomizer<RldItemStack> item, int level, int amount) {
    this.items = item;
    this.level = level;
    this.amount = amount;
  }

  public SingleUseLootRule(IWeighted<RldItemStack> item, int level, int amount) {
    this.items.add(item);
    this.level = level;
    this.amount = amount;
  }

  public void process(TreasureManager treasureManager) {
    if (isEmpty()) {
      return;
    }
    treasureManager.addItem(onLevelAndNotEmpty(level), items, amount);
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

}
