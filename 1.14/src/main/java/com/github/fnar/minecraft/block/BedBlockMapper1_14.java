package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.BedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BedPart;

public class BedBlockMapper1_14 {
  static BlockState map(BedBlock block) {
    return mapColor(block).getDefaultState()
        .with(net.minecraft.block.AnvilBlock.FACING, DirectionMapper1_14.map(block.getFacing()))
        .with(net.minecraft.block.BedBlock.PART, block.isHead() ? BedPart.HEAD : BedPart.FOOT);
  }

  private static Block mapColor(BedBlock block) {
    switch (block.getColor()) {
      case BLACK:
        return Blocks.BLACK_BED;
      case BLUE:
        return Blocks.BLUE_BED;
      case BROWN:
        return Blocks.BROWN_BED;
      case CYAN:
        return Blocks.CYAN_BED;
      case GRAY:
        return Blocks.GRAY_BED;
      case GREEN:
        return Blocks.GREEN_BED;
      case LIGHT_BLUE:
        return Blocks.LIGHT_BLUE_BED;
      case LIGHT_GRAY:
        return Blocks.LIGHT_GRAY_BED;
      case LIME:
        return Blocks.LIME_BED;
      case MAGENTA:
        return Blocks.MAGENTA_BED;
      case ORANGE:
        return Blocks.ORANGE_BED;
      case PINK:
        return Blocks.PINK_BED;
      case PURPLE:
        return Blocks.PURPLE_BED;
      case RED:
      default:
        return Blocks.RED_BED;
      case WHITE:
        return Blocks.WHITE_BED;
      case YELLOW:
        return Blocks.YELLOW_BED;
    }
  }
}
