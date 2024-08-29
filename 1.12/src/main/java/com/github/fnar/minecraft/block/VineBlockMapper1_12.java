package com.github.fnar.minecraft.block;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Direction;

public class VineBlockMapper1_12 {
  static IBlockState map(Direction dir) {
    return Blocks.VINE.getDefaultState()
        .withProperty(BlockVine.UP, dir == Direction.UP)
        .withProperty(BlockVine.NORTH, dir == Direction.NORTH)
        .withProperty(BlockVine.EAST, dir == Direction.EAST)
        .withProperty(BlockVine.SOUTH, dir == Direction.SOUTH)
        .withProperty(BlockVine.WEST, dir == Direction.WEST);
  }
}
