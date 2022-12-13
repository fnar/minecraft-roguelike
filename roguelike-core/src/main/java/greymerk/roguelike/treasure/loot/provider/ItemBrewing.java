package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Ingredient;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;
import greymerk.roguelike.util.WeightedRandomizer;


public class ItemBrewing extends LootItem {

  private final WeightedRandomizer<RldItemStack> items;

  public ItemBrewing(int weight, int level) {
    super(weight, level);
    this.items = new WeightedRandomizer<>();
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.SPIDER_EYE.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.BLAZE_POWDER.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.MAGMA_CREAM.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.GHAST_TEAR.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.NETHER_WART.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.REDSTONE.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.GLOWSTONE_DUST.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.SUGAR.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.SPECKLED_MELON.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.FERMENTED_SPIDER_EYE.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.BROWN_MUSHROOM.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.RED_MUSHROOM.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.RABBIT_FOOT.asItem(), 0, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.FISH.asItem(), 3, 1, 3, 1));
    this.items.add(new MinecraftItemLootItem(Ingredient.Type.GLASS_BOTTLE.asItem(), 0, 3, 12, 1));
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return this.items.get(random);
  }
}
