package greymerk.roguelike.dungeon.settings;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import greymerk.roguelike.treasure.Inventory;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;

// todo: there's already a mockChest. How similar are they?
class MockChest extends TreasureChest {

  Map<Integer, ItemStack> loot;

  public MockChest(ChestType type, int level) {
    super(type, level, null, 0, new Inventory(null, null));
    loot = new HashMap<>();
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

  public int getSize() {
    return 27;
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

}
