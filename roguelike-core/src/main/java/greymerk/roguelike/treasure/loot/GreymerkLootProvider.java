package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.HashMap;
import java.util.Map;

import greymerk.roguelike.util.IWeighted;

public class GreymerkLootProvider {

  private final Map<Integer, LootSettings> loot = new HashMap<>();

  public GreymerkLootProvider() {
    for (int i = 0; i < 5; ++i) {
      loot.put(i, new LootSettings(i));
    }
  }

  public void put(int level, LootSettings settings) {
    loot.put(level, settings);
  }

  public IWeighted<RldItemStack> get(GreymerkChestType type, int level) {
    if (level < 0) {
      return loot.get(0).get(type);
    }
    if (level > 4) {
      return loot.get(4).get(type);
    }
    return loot.get(level).get(type);
  }
}
