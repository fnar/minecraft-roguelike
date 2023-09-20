package com.github.fnar.minecraft.block;

import greymerk.roguelike.worldgen.Direction;

public class DirectionMapper1_14 {
  public static net.minecraft.util.Direction map(Direction direction) {
    // NOTE: I notice these are flipped but I'm not entirely sure why.
    // *** If you "fix" them, then all of the blocks in the dungeon are reversed.
    switch (direction) {
      default:
      case NORTH:
        return net.minecraft.util.Direction.SOUTH;
      case EAST:
        return net.minecraft.util.Direction.WEST;
      case SOUTH:
        return net.minecraft.util.Direction.NORTH;
      case WEST:
        return net.minecraft.util.Direction.EAST;
      case UP:
        return net.minecraft.util.Direction.UP;
      case DOWN:
        return net.minecraft.util.Direction.DOWN;
    }
  }
}
