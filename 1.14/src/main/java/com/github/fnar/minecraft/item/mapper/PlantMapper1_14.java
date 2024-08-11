package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.CouldNotMapException;
import com.github.fnar.minecraft.block.CouldNotMapBlockException;
import com.github.fnar.minecraft.block.decorative.PlantType;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public class PlantMapper1_14 {

  public ItemStack map(PlantType type) throws CouldNotMapBlockException {
    switch (type) {
      case POPPY:
        return new ItemStack(Blocks.POPPY);
      case DANDELION:
        return new ItemStack(Blocks.DANDELION);
      case BLUE_ORCHID:
        return new ItemStack(Blocks.BLUE_ORCHID);
      case ALLIUM:
        return new ItemStack(Blocks.ALLIUM);
      case AZURE_BLUET:
        return new ItemStack(Blocks.AZURE_BLUET);
      case RED_TULIP:
        return new ItemStack(Blocks.RED_TULIP);
      case ORANGE_TULIP:
        return new ItemStack(Blocks.ORANGE_TULIP);
      case WHITE_TULIP:
        return new ItemStack(Blocks.WHITE_TULIP);
      case PINK_TULIP:
        return new ItemStack(Blocks.PINK_TULIP);
      case OXEYE_DAISY:
        return new ItemStack(Blocks.OXEYE_DAISY);
      case RED_MUSHROOM:
        return new ItemStack(Blocks.RED_MUSHROOM);
      case BROWN_MUSHROOM:
        return new ItemStack(Blocks.BROWN_MUSHROOM);
      case CACTUS:
        return new ItemStack(Blocks.CACTUS);
      case OAK_SAPLING:
        return new ItemStack(Blocks.OAK_SAPLING);
      case SPRUCE_SAPLING:
        return new ItemStack(Blocks.SPRUCE_SAPLING);
      case BIRCH_SAPLING:
        return new ItemStack(Blocks.BIRCH_SAPLING);
      case JUNGLE_SAPLING:
        return new ItemStack(Blocks.JUNGLE_SAPLING);
      case ACACIA_SAPLING:
        return new ItemStack(Blocks.ACACIA_SAPLING);
      case DARK_OAK_SAPLING:
        return new ItemStack(Blocks.DARK_OAK_SAPLING);
      case GRASS:
        return new ItemStack(Blocks.GRASS);
      case DEAD_BUSH:
        return new ItemStack(Blocks.DEAD_BUSH);
      case FERN:
        return new ItemStack(Blocks.FERN);
      default:
        throw new CouldNotMapException(type.toString());
    }
  }
}
