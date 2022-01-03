package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.block.BlockType;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IngredientMapper1_12 extends BaseItemMapper1_12<Ingredient> {
  @Override
  public Class<Ingredient> getClazz() {
    return Ingredient.class;
  }

  @Override
  public ItemStack map(Ingredient item) {
    return new ItemStack(mapItem(item));
  }

  private Item mapItem(Ingredient item) {
    switch(item.getType()) {
      case BLAZE_POWDER:
        return Items.BLAZE_POWDER;
      case BROWN_MUSHROOM:
        return new BlockMapper1_12().map(BlockType.BROWN_MUSHROOM.asItem().asStack()).getItem();
      case FERMENTED_SPIDER_EYE:
        return Items.FERMENTED_SPIDER_EYE;
      case FISH:
        return Items.FISH;
      case GHAST_TEAR:
        return Items.GHAST_TEAR;
      case GLASS_BOTTLE:
        return Items.GLASS_BOTTLE;
      case GLOWSTONE_DUST:
        return Items.GLOWSTONE_DUST;
      case GUNPOWDER:
        return Items.GUNPOWDER;
      case MAGMA_CREAM:
        return Items.MAGMA_CREAM;
      case NETHER_WART:
        return Items.NETHER_WART;
      case RABBIT_FOOT:
        return Items.RABBIT_FOOT;
      case REDSTONE:
        return Items.REDSTONE;
      case RED_MUSHROOM:
        return new BlockMapper1_12().map(BlockType.RED_MUSHROOM.asItem().asStack()).getItem();
      case SPECKLED_MELON:
        return Items.SPECKLED_MELON;
      case SPIDER_EYE:
        return Items.SPIDER_EYE;
      case SUGAR:
        return Items.SUGAR;
    }
    throw new CouldNotMapItemException(item);
  }
}
