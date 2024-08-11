package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Ingredient;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IngredientMapper1_12 extends BaseItemMapper1_12<Ingredient> {
  @Override
  public Class<Ingredient> getClazz() {
    return Ingredient.class;
  }

  @Override
  public ItemStack map(Ingredient item) throws CouldNotMapItemException {
    if (Ingredient.Type.PUFFERFISH.equals(item.getType())) {
      return new ItemStack(Items.FISH, 3);
    }
    return addEnchantmentNbtTags(item, mapItem(item));
  }

  private Item mapItem(Ingredient item) throws CouldNotMapItemException {
    switch (item.getType()) {
      case BLAZE_POWDER:
        return Items.BLAZE_POWDER;
      case BROWN_MUSHROOM:
        return new BlockItemMapper1_12().map(BlockType.BROWN_MUSHROOM.asItem().asStack()).getItem();
      case FERMENTED_SPIDER_EYE:
        return Items.FERMENTED_SPIDER_EYE;
      case PUFFERFISH:
        return Items.FISH;
      case GHAST_TEAR:
        return Items.GHAST_TEAR;
      case GLASS_BOTTLE:
        return Items.GLASS_BOTTLE;
      case GLISTERING_MELON_SLICE:
        return Items.SPECKLED_MELON;
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
        return new BlockItemMapper1_12().map(BlockType.RED_MUSHROOM.asItem().asStack()).getItem();
      case SPIDER_EYE:
        return Items.SPIDER_EYE;
      case SUGAR:
        return Items.SUGAR;
    }
    throw new CouldNotMapItemException(item);
  }
}
