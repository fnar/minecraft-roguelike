package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.LeverBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.AttachFace;

import greymerk.roguelike.worldgen.Direction;

public class LeverMapper1_14 {
  static BlockState getLever(LeverBlock leverBlock) {
    Direction dir = leverBlock.getFacing().reverse();
    BlockState state = Blocks.LEVER.getDefaultState()
        .with(net.minecraft.block.LeverBlock.FACE, getLeverOrientation(dir))
        .with(net.minecraft.block.LeverBlock.POWERED, leverBlock.isActive());
    return dir.isCardinal()
        ? state.with(net.minecraft.block.LeverBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(dir))
        : state;
  }

  private static AttachFace getLeverOrientation(Direction direction) {
    switch (direction) {
      default:
      case NORTH:
      case EAST:
      case SOUTH:
      case WEST:
        return AttachFace.WALL;
      case UP:
        return AttachFace.CEILING;
      case DOWN:
        return AttachFace.FLOOR;
    }
  }
}
