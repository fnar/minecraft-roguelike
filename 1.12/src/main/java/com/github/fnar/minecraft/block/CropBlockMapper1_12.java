package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.material.Crop;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class CropBlockMapper1_12 {
  public static IBlockState map(CropBlock cropBlock) {
    IBlockState blockState = mapToBlock(cropBlock).getDefaultState();
    if (cropBlock.getCrop().equals(Crop.COCOA)) {
      return blockState
          .withProperty(BlockCocoa.FACING, DirectionMapper1_12.map(cropBlock.getFacing().reverse()))
          .withProperty(BlockCocoa.AGE, 2);
    }
    return blockState;
  }

  static Block mapToBlock(CropBlock cropBlock) {
    switch (cropBlock.getCrop()) {
      case BEETROOTS:
        return Blocks.BEETROOTS;
      case CARROTS:
        return Blocks.CARROTS;
      case COCOA:
        return Blocks.COCOA;
      case MELON:
        return Blocks.MELON_STEM;
      case NETHER_WART:
        return Blocks.NETHER_WART;
      case POTATOES:
        return Blocks.POTATOES;
      case PUMPKIN:
        return Blocks.PUMPKIN_STEM;
      default:
      case WHEAT:
        return Blocks.WHEAT;
    }
  }
}
