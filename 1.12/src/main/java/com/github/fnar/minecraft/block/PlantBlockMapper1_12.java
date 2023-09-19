package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.PlantBlock;
import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class PlantBlockMapper1_12 {

  public static IBlockState getPlant(PlantBlock block) {
    return getPlant(block.getPlant());
  }

  private static IBlockState getPlant(Plant type) {
    switch (type) {
      case POPPY:
        return Blocks.RED_FLOWER.getDefaultState();
      case BLUE_ORCHID:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID);
      case ALLIUM:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ALLIUM);
      case AZURE_BLUET:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.HOUSTONIA);
      case RED_TULIP:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.RED_TULIP);
      case ORANGE_TULIP:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ORANGE_TULIP);
      case WHITE_TULIP:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.WHITE_TULIP);
      case PINK_TULIP:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.PINK_TULIP);
      case OXEYE_DAISY:
        return Blocks.RED_FLOWER.getDefaultState()
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.OXEYE_DAISY);
      case RED_MUSHROOM:
        return Blocks.RED_MUSHROOM.getDefaultState();
      case BROWN_MUSHROOM:
        return Blocks.BROWN_MUSHROOM.getDefaultState();
      case CACTUS:
        return Blocks.CACTUS.getDefaultState();
      case OAK_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.OAK);
      case SPRUCE_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.BIRCH);
      case JUNGLE_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.DARK_OAK);
      case DEAD_BUSH:
        return Blocks.TALLGRASS.getDefaultState()
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.DEAD_BUSH);
      case FERN:
        return Blocks.TALLGRASS.getDefaultState()
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN);
      case DANDELION:
      default:
        return Blocks.YELLOW_FLOWER.getDefaultState();
    }
  }
}
