package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TreasureChest {

  private final ChestType chestType;
  private final int level;
  private final Inventory inventory;
  private final Coord pos;
  private final WorldEditor worldEditor;

  public TreasureChest(
      ChestType chestType,
      int level,
      Coord pos,
      WorldEditor worldEditor
  ) {
    this.chestType = chestType;
    this.level = level;
    this.inventory = new Inventory(worldEditor.getRandom(), (TileEntityChest) worldEditor.getTileEntity(pos));
    this.pos = pos;
    this.worldEditor = worldEditor;
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

  public void setLootTable(String table) {
    worldEditor.setLootTable(pos, table);
  }

  public boolean isNotEmpty() {
    return !isEmpty();
  }

  private boolean isEmpty() {
    return chestType == null || getType().equals(ChestType.EMPTY);
  }
}
