package greymerk.roguelike.treasure;

import com.github.srwaggon.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;

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
  public final WorldEditor worldEditor;

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

  public void setRandomEmptySlot(ItemStack itemStack) {
    List<Integer> slots = IntStream.range(0, this.worldEditor.getCapacity(this)).boxed().collect(Collectors.toList());
    Collections.shuffle(slots);
    slots.stream()
        .mapToInt(slot -> slot)
        .filter(slot -> worldEditor.isEmptySlot(this, slot))
        .findFirst()
        .ifPresent(value -> setSlot(value, itemStack));
  }

  public void setSlot(int slot, RldItemStack itemStack) {
    worldEditor.setItem(pos, slot, itemStack);
  }

  public void setRandomEmptySlot(RldItemStack itemStack) {
    List<Integer> slots = IntStream.range(0, this.worldEditor.getCapacity(this)).boxed().collect(Collectors.toList());
    Collections.shuffle(slots);
    slots.stream()
        .mapToInt(slot -> slot)
        .filter(slot -> worldEditor.isEmptySlot(this, slot))
        .findFirst()
        .ifPresent(value -> setSlot(value, itemStack));
  }

  public ChestType getType() {
    return chestType;
  }

  public Coord getPos() {
    return pos;
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
