package greymerk.roguelike.dungeon.settings;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

// todo: there's already a mockChest. How similar are they?
class MockChest extends TreasureChest {

  Treasure type;
  int level;
  Map<Integer, ItemStack> loot;

  public MockChest(Treasure type, int level) {
    super(type, level, false);
    this.type = type;
    loot = new HashMap<>();
  }

  public MockChest generate(IWorldEditor editor, Random rand, Coord pos) {
    return this;
  }

  public boolean setSlot(int slot, ItemStack item) {
    loot.put(slot, item);
    return true;
  }

  public boolean setRandomEmptySlot(ItemStack item) {
    for (int i = 0; i < getSize(); ++i) {
      if (!isEmptySlot(i)) {
        continue;
      }
      setSlot(i, item);
      return true;
    }

    return false;
  }

  public boolean isEmptySlot(int slot) {
    return !loot.containsKey(slot);
  }

  public Treasure getType() {
    return type;
  }

  public int getSize() {
    return 27;
  }

  public int getLevel() {
    return level;
  }

  public int count(ItemStack type) {
    int count = 0;

    for (int i = 0; i < getSize(); ++i) {
      if (!loot.containsKey(i)) {
        continue;
      }
      ItemStack item = loot.get(i);
      if (item.getItem() != type.getItem()) {
        continue;
      }
      count += item.getCount();
    }

    return count;
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
