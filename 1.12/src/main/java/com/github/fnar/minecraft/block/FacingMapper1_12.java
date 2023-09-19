package com.github.fnar.minecraft.block;

import net.minecraft.util.EnumFacing;

import greymerk.roguelike.worldgen.Direction;

public class FacingMapper1_12 {
  public static EnumFacing getFacing(Direction direction) {
    // NOTE: I notice these are flipped but I'm not entirely sure why.
    // *** If you "fix" them, then all of the blocks in the dungeon are reversed.
    switch (direction) {
      default:
      case NORTH:
        return EnumFacing.SOUTH;
      case EAST:
        return EnumFacing.WEST;
      case SOUTH:
        return EnumFacing.NORTH;
      case WEST:
        return EnumFacing.EAST;
      case UP:
        return EnumFacing.UP;
      case DOWN:
        return EnumFacing.DOWN;
    }
  }
}
