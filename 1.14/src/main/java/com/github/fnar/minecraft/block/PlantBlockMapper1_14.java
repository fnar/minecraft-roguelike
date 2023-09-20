package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.CouldNotMapException;
import com.github.fnar.minecraft.block.decorative.PlantBlock;
import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class PlantBlockMapper1_14 {

  public static BlockState getPlant(PlantBlock block) {
    switch (block.getPlant()) {
      case POPPY:
        return Blocks.POPPY.getDefaultState();
      case BLUE_ORCHID:
        return Blocks.BLUE_ORCHID.getDefaultState();
      case ALLIUM:
        return Blocks.ALLIUM.getDefaultState();
      case AZURE_BLUET:
        return Blocks.AZURE_BLUET.getDefaultState();
      case RED_TULIP:
        return Blocks.RED_TULIP.getDefaultState();
      case ORANGE_TULIP:
        return Blocks.ORANGE_TULIP.getDefaultState();
      case WHITE_TULIP:
        return Blocks.WHITE_TULIP.getDefaultState();
      case PINK_TULIP:
        return Blocks.PINK_TULIP.getDefaultState();
      case OXEYE_DAISY:
        return Blocks.OXEYE_DAISY.getDefaultState();
      case RED_MUSHROOM:
        return Blocks.RED_MUSHROOM.getDefaultState();
      case BROWN_MUSHROOM:
        return Blocks.BROWN_MUSHROOM.getDefaultState();
      case CACTUS:
        return Blocks.CACTUS.getDefaultState();
      case OAK_SAPLING:
        return WoodMapper1_14.map(Wood.OAK);
      case SPRUCE_SAPLING:
        return WoodMapper1_14.map(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return WoodMapper1_14.map(Wood.BIRCH);
      case JUNGLE_SAPLING:
        return WoodMapper1_14.map(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return WoodMapper1_14.map(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return WoodMapper1_14.map(Wood.DARK_OAK);
      case DEAD_BUSH:
        return Blocks.DEAD_BUSH.getDefaultState();
      case FERN:
        return Blocks.FERN.getDefaultState();
      case DANDELION:
        return Blocks.DANDELION.getDefaultState();
    }
    throw new CouldNotMapException(block.toString());
  }

}
