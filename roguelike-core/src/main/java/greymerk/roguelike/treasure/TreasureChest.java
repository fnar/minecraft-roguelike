package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TreasureChest {

  private final ChestType chestType;
  private final int level;
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
    this.pos = pos;
    this.worldEditor = worldEditor;
  }

  public void setSlot(int slot, ItemStack item) {
    worldEditor.setItem(pos, slot, item);
  }

  public void setRandomEmptySlot(ItemStack item) {
    List<Integer> slots = IntStream.range(0, getSize()).boxed().collect(Collectors.toList());
    Collections.shuffle(slots);
    slots.stream()
        .mapToInt(slot -> slot)
        .filter(this::isEmptySlot)
        .findFirst()
        .ifPresent(value -> setSlot(value, item));
  }

  public TileEntityChest getTileEntity() {
    return (TileEntityChest) worldEditor.getTileEntity(pos);
  }

  public boolean isEmptySlot(int slot) {
    return getTileEntity().getStackInSlot(slot).isEmpty();
  }

  public ChestType getType() {
    return chestType;
  }

  public int getSize() {
    return getTileEntity().getSizeInventory();
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
