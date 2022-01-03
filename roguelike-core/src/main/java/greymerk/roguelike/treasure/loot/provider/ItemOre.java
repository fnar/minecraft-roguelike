package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ItemOre extends LootItem {

  private final Map<Integer, WeightedRandomizer<RldItemStack>> loot;

  public ItemOre(int weight, int level) {
    super(weight, level);
    this.loot = new HashMap<>();
    for (int i = 0; i < 5; ++i) {

      WeightedRandomizer<RldItemStack> randomizer = new WeightedRandomizer<>();

      switch (i) {
        case 4:
          randomizer.add(new MinecraftItemLootItem(Material.Type.DIAMOND.asItem(), 0, 1, 1, 1));
          randomizer.add(new MinecraftItemLootItem(Material.Type.EMERALD.asItem(), 0, 1, 1, 2));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 2, 5, 3));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_NUGGET.asItem(), 0, 2, 8, 2));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_INGOT.asItem(), 0, 2, 5, 5));
          break;
        case 3:
          randomizer.add(new MinecraftItemLootItem(Material.Type.DIAMOND.asItem(), 0, 1, 1, 1));
          randomizer.add(new MinecraftItemLootItem(Material.Type.EMERALD.asItem(), 0, 1, 1, 2));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 1, 5, 3));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_NUGGET.asItem(), 0, 2, 6, 5));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_INGOT.asItem(), 0, 1, 4, 10));
          randomizer.add(new MinecraftItemLootItem(Material.Type.COAL.asItem(), 0, 4, 15, 3));
          break;
        case 2:
          randomizer.add(new MinecraftItemLootItem(Material.Type.DIAMOND.asItem(), 0, 1, 1, 1));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 1, 4, 3));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_NUGGET.asItem(), 0, 1, 5, 5));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_INGOT.asItem(), 0, 1, 3, 10));
          randomizer.add(new MinecraftItemLootItem(Material.Type.COAL.asItem(), 0, 3, 7, 10));
          break;
        case 1:
          randomizer.add(new MinecraftItemLootItem(Material.Type.DIAMOND.asItem(), 0, 1, 1, 1));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 1, 3, 5));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_NUGGET.asItem(), 0, 1, 4, 10));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_INGOT.asItem(), 0, 1, 2, 5));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_NUGGET.asItem(), 0, 1, 5, 20));
          randomizer.add(new MinecraftItemLootItem(Material.Type.COAL.asItem(), 0, 2, 5, 20));
          randomizer.add(new MinecraftItemLootItem(Material.Type.LEATHER.asItem(), 0, 3, 9, 10));
          break;
        case 0:
          randomizer.add(new MinecraftItemLootItem(Material.Type.DIAMOND.asItem(), 0, 1, 1, 1));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 1, 1, 3));
          randomizer.add(new MinecraftItemLootItem(Material.Type.GOLD_NUGGET.asItem(), 0, 1, 2, 15));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_INGOT.asItem(), 0, 1, 1, 10));
          randomizer.add(new MinecraftItemLootItem(Material.Type.IRON_NUGGET.asItem(), 0, 1, 5, 30));
          randomizer.add(new MinecraftItemLootItem(Material.Type.COAL.asItem(), 0, 1, 3, 40));
          randomizer.add(new MinecraftItemLootItem(Material.Type.LEATHER.asItem(), 0, 1, 7, 15));
          break;
        default:
          randomizer.add(new WeightedChoice<>(Material.Type.COAL.asItem().asStack(), 1));
      }

      loot.put(i, randomizer);
    }
  }

  @Override
  public RldItemStack getLootItem(Random rand, int level) {
    return this.loot.get(level).get(rand);
  }
}
