package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.CouldNotMapException;
import com.github.fnar.minecraft.block.decorative.Plant;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class PlantMapper1_12 {

  public ItemStack map(Plant type) {
    switch (type) {
      case POPPY:
        return new ItemStack(Blocks.RED_FLOWER, 1, 0);
      case DANDELION:
        return new ItemStack(Blocks.YELLOW_FLOWER);
      case BLUE_ORCHID:
        return new ItemStack(Blocks.RED_FLOWER, 1, 1);
      case ALLIUM:
        return new ItemStack(Blocks.RED_FLOWER, 1, 2);
      case AZURE_BLUET:
        return new ItemStack(Blocks.RED_FLOWER, 1, 3);
      case RED_TULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 4);
      case ORANGE_TULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 5);
      case WHITE_TULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 6);
      case PINK_TULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 7);
      case OXEYE_DAISY:
        return new ItemStack(Blocks.RED_FLOWER, 1, 8);
      case RED_MUSHROOM:
        return new ItemStack(Blocks.RED_MUSHROOM);
      case BROWN_MUSHROOM:
        return new ItemStack(Blocks.BROWN_MUSHROOM);
      case CACTUS:
        return new ItemStack(Blocks.CACTUS);
      case OAK_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 0);
      case SPRUCE_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 1);
      case BIRCH_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 2);
      case JUNGLE_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 3);
      case ACACIA_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 4);
      case DARK_OAK_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 5);
      case GRASS:
        return new ItemStack(Blocks.TALLGRASS, 1, 0);
      case DEAD_BUSH:
        return new ItemStack(Blocks.TALLGRASS, 1, 0);
      case FERN:
        return new ItemStack(Blocks.TALLGRASS, 1, 2);
      default:
        throw new CouldNotMapException(type.toString());
    }
  }

}
