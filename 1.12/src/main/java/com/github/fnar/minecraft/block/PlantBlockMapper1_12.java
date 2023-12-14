package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.PlantBlock;
import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class PlantBlockMapper1_12 {

  public static IBlockState map(PlantBlock block) throws CouldNotMapBlockException {
    switch (block.getPlant()) {
      case POPPY:
        return Blocks.RED_FLOWER.getDefaultState();
      case DANDELION:
        return Blocks.YELLOW_FLOWER.getDefaultState();
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
        return WoodMapper1_12.mapSapling(Wood.OAK);
      case SPRUCE_SAPLING:
        return WoodMapper1_12.mapSapling(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return WoodMapper1_12.mapSapling(Wood.BIRCH);
      case JUNGLE_SAPLING:
        return WoodMapper1_12.mapSapling(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return WoodMapper1_12.mapSapling(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return WoodMapper1_12.mapSapling(Wood.DARK_OAK);
      case GRASS:
        return Blocks.TALLGRASS.getDefaultState()
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
      case DEAD_BUSH:
        return Blocks.TALLGRASS.getDefaultState()
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.DEAD_BUSH);
      case FERN:
        return Blocks.TALLGRASS.getDefaultState()
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN);
      default:
        throw new CouldNotMapBlockException(block.toString());
    }
  }

}
