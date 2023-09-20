package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.Quartz;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;

import greymerk.roguelike.worldgen.Direction;

public class QuartzBlockMapper1_14 {

  public static BlockState map(Quartz type, Direction facing) {
    switch (type) {
      case SMOOTH:
        return Blocks.QUARTZ_BLOCK.getDefaultState();
      case CHISELED:
        return Blocks.CHISELED_QUARTZ_BLOCK.getDefaultState();
      case PILLAR:
        return Blocks.QUARTZ_PILLAR.getDefaultState().with(RotatedPillarBlock.AXIS, getAxis(facing));
      case BRICKS:
        return Blocks.QUARTZ_BLOCK.getDefaultState();
      default:
        throw new IllegalStateException("Unexpected value: " + type);
    }
  }

  private static net.minecraft.util.Direction.Axis getAxis(Direction facing) {
    switch (facing) {
      case NORTH:
      case SOUTH:
        return net.minecraft.util.Direction.Axis.Z;
      case EAST:
      case WEST:
        return net.minecraft.util.Direction.Axis.X;
      case UP:
      case DOWN:
        return net.minecraft.util.Direction.Axis.Y;
      default:
        throw new IllegalStateException("Unexpected value: " + facing);
    }
  }

}
