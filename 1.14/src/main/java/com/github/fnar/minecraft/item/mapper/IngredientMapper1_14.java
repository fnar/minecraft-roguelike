package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Ingredient;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class IngredientMapper1_14 extends BaseItemMapper1_14<Ingredient> {
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
        return new BlockItemMapper1_14().map(BlockType.BROWN_MUSHROOM.asItem().asStack()).getItem();
      case FERMENTED_SPIDER_EYE:
        return Items.FERMENTED_SPIDER_EYE;
      case GHAST_TEAR:
        return Items.GHAST_TEAR;
      case GLASS_BOTTLE:
        return Items.GLASS_BOTTLE;
      case GLISTERING_MELON_SLICE:
        return Items.GLISTERING_MELON_SLICE;
      case GLOWSTONE_DUST:
        return Items.GLOWSTONE_DUST;
      case GUNPOWDER:
        return Items.GUNPOWDER;
      case MAGMA_CREAM:
        return Items.MAGMA_CREAM;
      case NETHER_WART:
        return Items.NETHER_WART;
      case PUFFERFISH:
        return Items.PUFFERFISH;
      case RABBIT_FOOT:
        return Items.RABBIT_FOOT;
      case REDSTONE:
        return Items.REDSTONE;
      case RED_MUSHROOM:
        return new BlockItemMapper1_14().map(BlockType.RED_MUSHROOM.asItem().asStack()).getItem();
      case SPIDER_EYE:
        return Items.SPIDER_EYE;
      case SUGAR:
        return Items.SUGAR;
    }
    throw new CouldNotMapItemException(item);
  }
}
