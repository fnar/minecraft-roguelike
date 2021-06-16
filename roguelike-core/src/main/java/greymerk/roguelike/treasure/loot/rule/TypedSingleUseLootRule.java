package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.ofTypeOnLevel;

public class TypedSingleUseLootRule implements LootRule {

  private final ChestType chestType;
  private final int level;
  private WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>();
  private final int amount;

  public TypedSingleUseLootRule(ChestType chestType, WeightedRandomizer<ItemStack> item, int level, int amount) {
    this.chestType = chestType;
    this.items = item;
    this.level = level;
    this.amount = amount;
  }

  public TypedSingleUseLootRule(ChestType chestType, IWeighted<ItemStack> item, int level, int amount) {
    this.chestType = chestType;
    this.items.add(item);
    this.level = level;
    this.amount = amount;
  }

  @Override
  public void process(TreasureManager treasureManager) {
    if (isEmpty()) {
      return;
    }
    treasureManager.addItem(ofTypeOnLevel(chestType, level), items, amount);
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

}
