package greymerk.roguelike.treasure;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.lang.Math.*;

public class TreasureChest {

  protected Inventory inventory;
  protected Treasure type;
  protected Random rand;
  private long seed;
  private TileEntityChest chest;
  private int level;
  private final boolean isTrapped;

  public TreasureChest(
      Treasure type,
      int level,
      boolean isTrapped
  ) {
    this.type = type;
    this.level = level;
    this.isTrapped = isTrapped;
  }

  public TreasureChest generate(WorldEditor worldEditor, Random rand, Coord pos) throws ChestPlacementException {
    this.rand = rand;
    MetaBlock chestType = new MetaBlock(isTrapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);

    boolean success = chestType.set(worldEditor, pos);

    if (!success) {
      throw new ChestPlacementException("Failed to place chest in world");
    }

    this.chest = (TileEntityChest) worldEditor.getTileEntity(pos);
    this.inventory = new Inventory(rand, chest);
    this.seed = Objects.hash(pos.hashCode(), worldEditor.getSeed());

    worldEditor.addChest(this);
    return this;
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

  public Treasure getType() {
    return this.type;
  }

  public int getSize() {
    return this.inventory.getInventorySize();
  }

  public int getLevel() {
    return max(0, min(level, 4));
  }

  public void setLootTable(ResourceLocation table) {
    this.chest.setLootTable(table, seed);
  }

  public boolean isOnLevel(int level) {
    return getLevel() == level;
  }

  public boolean isType(Treasure type) {
    return getType() == type;
  }

  public boolean isNotEmpty() {
    return !isEmpty();
  }

  private boolean isEmpty() {
    return isType(Treasure.EMPTY);
  }
}
