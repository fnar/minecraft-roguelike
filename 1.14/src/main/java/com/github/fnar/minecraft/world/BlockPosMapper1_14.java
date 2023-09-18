package com.github.fnar.minecraft.world;

import net.minecraft.util.math.BlockPos;

import greymerk.roguelike.worldgen.Coord;

public class BlockPosMapper1_14 {

  public static Coord map(BlockPos blockPos) {
    return new Coord(
        blockPos.getX(),
        blockPos.getY(),
        blockPos.getZ());
  }

  public static BlockPos map(Coord pos) {
    return new BlockPos(
        pos.getX(),
        pos.getY(),
        pos.getZ());
  }

}
