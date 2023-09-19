package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.LeverBlock;

import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Direction;

public class LeverMapper1_12 {
  static IBlockState getLever(LeverBlock leverBlock) {
    Direction dir = leverBlock.getFacing();
    return Blocks.LEVER.getDefaultState()
        .withProperty(BlockLever.POWERED, leverBlock.isActive())
        .withProperty(BlockLever.FACING, getLeverOrientation(dir));
  }

  private static BlockLever.EnumOrientation getLeverOrientation(Direction direction) {
    switch (direction) {
      default:
      case NORTH:
        return BlockLever.EnumOrientation.NORTH;
      case EAST:
        return BlockLever.EnumOrientation.EAST;
      case SOUTH:
        return BlockLever.EnumOrientation.SOUTH;
      case WEST:
        return BlockLever.EnumOrientation.WEST;
      case UP:
        return BlockLever.EnumOrientation.UP_X;
      case DOWN:
        return BlockLever.EnumOrientation.DOWN_X;
    }
  }
}
