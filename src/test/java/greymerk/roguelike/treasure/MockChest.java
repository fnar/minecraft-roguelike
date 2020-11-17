package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class MockChest extends TreasureChest {

  Treasure type;
  int level;
  Inventory inv;
  TileEntityChest chest;

  public MockChest(Treasure type, int level) {
    super(type, level, false);
    this.type = type;
    this.chest = new TileEntityChest();
    this.inv = new Inventory(new Random(), chest);
  }

  public boolean setSlot(int slot, ItemStack item) {
    return super.setSlot(slot, item);
  }

  public boolean setRandomEmptySlot(ItemStack item) {
    return this.inv.setRandomEmptySlot(item);
  }

  public boolean isEmptySlot(int slot) {
    return this.inv.isEmptySlot(slot);
  }

  public Treasure getType() {
    return this.type;
  }

  public int getSize() {
    return this.inv.getInventorySize();
  }

  public int getLevel() {
    return this.level;
  }

  public boolean contains(ItemStack item) {

    for (int i = 0; i < 27; ++i) {
      ItemStack slot = chest.getStackInSlot(i);
      if (sameItem(item, slot)) {
        return true;
      }
    }

    return false;
  }

  private boolean sameItem(ItemStack item, ItemStack other) {
    if (item == other) {
      return true;
    }
    if (item != null && other == null) {
      return false;
    }
    if (item == null) {
      return false;
    }
    if (item.getItem() != other.getItem()) {
      return false;
    }
    return item.getItemDamage() == other.getItemDamage();
  }

  public void setLootTable(ResourceLocation table) {

  }

  public boolean isOnLevel(int level) {
    return getLevel() == level;
  }

  public boolean isType(Treasure type) {
    return getType() == type;
  }

  public boolean isNotEmpty() {
    return !isType(Treasure.EMPTY);
  }
}
