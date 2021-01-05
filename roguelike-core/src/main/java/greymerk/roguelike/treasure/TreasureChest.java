package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import greymerk.roguelike.treasure.loot.ChestType;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TreasureChest {

  private final Inventory inventory;
  private ChestType chestType;
  private final long seed;
  private final TileEntityChest tileEntityChest;
  private final int level;

  public TreasureChest(
      ChestType chestType,
      int level,
      TileEntityChest tileEntityChest,
      int seed,
      Inventory inventory
  ) {
    this.chestType = chestType;
    this.level = level;
    this.tileEntityChest = tileEntityChest;
    this.inventory = inventory;
    this.seed = seed;
  }

  public boolean setSlot(int slot, ItemStack item) {
    return this.inventory.setInventorySlot(slot, item);
  }

  public boolean setRandomEmptySlot(ItemStack item) {
    return this.inventory.setRandomEmptySlot(item);
  }

  public boolean isEmptySlot(int slot) {
    return this.inventory.isEmptySlot(slot);
  }

  public ChestType getType() {
    return chestType;
  }

  public int getSize() {
    return this.inventory.getInventorySize();
  }

  public int getLevel() {
    return max(0, min(level, 4));
  }

  public void setLootTable(ResourceLocation table) {
    this.tileEntityChest.setLootTable(table, seed);
  }

  public boolean isOnLevel(int level) {
    return getLevel() == level;
  }

  public boolean isType(ChestType chestType) {
    return getType().equals(chestType);
  }

  public boolean isNotEmpty() {
    return !isEmpty();
  }

  private boolean isEmpty() {
    return chestType == null || isType(ChestType.EMPTY);
  }
}
