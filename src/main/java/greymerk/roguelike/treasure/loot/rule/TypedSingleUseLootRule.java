package greymerk.roguelike.treasure.loot.rule;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.IWeighted;

import static greymerk.roguelike.treasure.TreasureManager.ofTypeOnLevel;

public class TypedSingleUseLootRule implements LootRule {

  private ChestType chestType;
  private final IWeighted<ItemStack> item;
  private final int level;
  private final int amount;

  public TypedSingleUseLootRule(ChestType chestType, IWeighted<ItemStack> item, int level, int amount) {
    this.chestType = chestType;
    this.item = item;
    this.level = level;
    this.amount = amount;
  }

  @Override
  public void process(TreasureManager treasureManager) {
    treasureManager.addItem(ofTypeOnLevel(chestType, level), item, amount);
  }
}
