package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.material.Crop;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;

public class CropBlockMapper1_14 {
  public static BlockState map(CropBlock cropBlock) {
    BlockState blockState = mapToBlock(cropBlock).getDefaultState();
    if (cropBlock.getCrop().equals(Crop.COCOA)) {
      return blockState
          .with(CocoaBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(cropBlock.getFacing().reverse()))
          .with(CocoaBlock.AGE, 2);
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
