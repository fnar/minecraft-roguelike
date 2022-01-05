package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Food;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ItemFood extends LootItem {

  private Map<Integer, WeightedRandomizer<RldItemStack>> loot;

  public ItemFood(int weight, int level) {
    super(weight, level);
    this.loot = new HashMap<>();
    for (int i = 0; i < 5; ++i) {

      WeightedRandomizer<RldItemStack> randomizer = new WeightedRandomizer<>();

      switch (i) {
        case 4:
          randomizer.add(new WeightedChoice<>(Food.Type.GOLDEN_APPLE.asItem().asStack(),1));
          randomizer.add(new MinecraftItemLootItem(Food.Type.GOLDEN_CARROT.asItem(), 0, 1, 1, 2));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_BEEF.asItem(), 0, 1, 5, 3));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_PORKCHOP.asItem(), 0, 1, 5, 3));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_MUTTON.asItem(), 0, 1, 5, 3));
          break;
        case 3:
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_BEEF.asItem(), 0, 1, 3, 3));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_PORKCHOP.asItem(), 0, 1, 3, 3));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_MUTTON.asItem(), 0, 1, 3, 3));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_CHICKEN.asItem(), 0, 1, 2, 1));
          randomizer.add(new MinecraftItemLootItem(Food.Type.BAKED_POTATO.asItem(), 0, 1, 2, 1));
          break;
        case 2:
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_BEEF.asItem(), 0, 1, 3, 1));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_PORKCHOP.asItem(), 0, 1, 3, 1));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_MUTTON.asItem(), 0, 1, 3, 1));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_CHICKEN.asItem(), 0, 1, 2, 2));
          randomizer.add(new MinecraftItemLootItem(Food.Type.BAKED_POTATO.asItem(), 0, 1, 2, 2));
          break;
        case 1:
          randomizer.add(new MinecraftItemLootItem(Food.Type.BREAD.asItem(), 0, 1, 3, 5));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_FISH.asItem(), 0, 1, 3, 5));
          randomizer.add(new MinecraftItemLootItem(Food.Type.APPLE.asItem(), 0, 1, 3, 2));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_CHICKEN.asItem(), 0, 1, 2, 2));
          randomizer.add(new MinecraftItemLootItem(Food.Type.BAKED_POTATO.asItem(), 0, 1, 2, 2));
          break;
        case 0:
          randomizer.add(new MinecraftItemLootItem(Food.Type.BREAD.asItem(), 0, 1, 2, 5));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKED_FISH.asItem(), 0, 1, 2, 5));
          randomizer.add(new MinecraftItemLootItem(Food.Type.APPLE.asItem(), 0, 1, 2, 5));
          randomizer.add(new MinecraftItemLootItem(Food.Type.COOKIE.asItem(), 0, 1, 4, 1));
          break;
        default:
          randomizer.add(new WeightedChoice<>(Food.Type.BREAD.asItem().asStack(), 1));
      }

      loot.put(i, randomizer);
    }
  }

  @Override
  public RldItemStack getLootItem(Random random, int level) {
    return this.loot.get(level).get(random);
  }


}
