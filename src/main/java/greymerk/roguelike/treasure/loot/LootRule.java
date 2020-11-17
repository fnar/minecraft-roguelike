package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import java.util.Random;

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


  public void process(Random rand, TreasureManager treasureManager) {
    if (toEach && treasureType != null) {
      treasureManager.addItemToAll(rand, treasureType, level, item, amount);
    }
    if (toEach && treasureType == null) {
      treasureManager.addItemToAll(rand, level, item, amount);
    }
    if (!toEach && treasureType != null) {
      treasureManager.addItem(rand, treasureType, level, item, amount);
    }
    if (!toEach && treasureType == null) {
      treasureManager.addItem(rand, level, item, amount);
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
