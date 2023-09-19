package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.Quartz;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Direction;

public class QuartzBlockMapper1_12 {

  public static IBlockState map(Quartz type, Direction facing) {
    BlockQuartz.EnumType variant = Quartz.SMOOTH.equals(type)
        ? BlockQuartz.EnumType.DEFAULT
        : Quartz.CHISELED.equals(type)
            ? BlockQuartz.EnumType.CHISELED
            : getPillarLines(facing);
    return Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, variant);
  }

  private static BlockQuartz.EnumType getPillarLines(Direction facing) {
    switch (facing) {
      case EAST:
      case WEST:
        return BlockQuartz.EnumType.LINES_X;
      case NORTH:
      case SOUTH:
        return BlockQuartz.EnumType.LINES_Z;
      case UP:
      case DOWN:
      default:
        return BlockQuartz.EnumType.LINES_Y;
    }
  }
}
