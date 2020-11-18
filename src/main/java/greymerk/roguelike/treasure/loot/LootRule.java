package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.IWeighted;

public class LootRule {

  int level;
  int amount;
  private final Treasure treasureType;
  private final IWeighted<ItemStack> item;
  private final boolean toEach;

  public LootRule(Treasure treasureType, IWeighted<ItemStack> item, int level, boolean toEach, int amount) {
    this.treasureType = treasureType;
    this.item = item;
    this.level = level;
    this.toEach = toEach;
    this.amount = amount;
  }


  public void process(TreasureManager treasureManager) {
    if (toEach && treasureType != null) {
      treasureManager.addItemToAll(treasureType, level, item, amount);
    }
    if (toEach && treasureType == null) {
      treasureManager.addItemToAll(level, item, amount);
    }
    if (!toEach && treasureType != null) {
      treasureManager.addItem(treasureType, level, item, amount);
    }
    if (!toEach && treasureType == null) {
      treasureManager.addItem(level, item, amount);
    }
  }

  @Override
  public String toString() {

    String type = this.treasureType != null ? this.treasureType.toString() : "NONE";
    int level = this.level;
    int amount = this.amount;

    return "type: " + type + " level: " + level + " amount: " + amount;
  }
}
