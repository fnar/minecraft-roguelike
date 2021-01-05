package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

import greymerk.roguelike.treasure.loot.ChestType;

public class MockChest extends TreasureChest {

  Inventory inv;
  TileEntityChest chest;

  public MockChest(ChestType chestType, int level) {
    super(chestType, level, null, 0, new Inventory(null, null));
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

  public int getSize() {
    return this.inv.getInventorySize();
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

}
