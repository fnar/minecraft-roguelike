package com.github.fnar.minecraft.block;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Direction;

public class VineBlockMapper1_12 {
  static IBlockState map(Direction dir) {
    IBlockState vine = Blocks.VINE.getDefaultState();
    vine.withProperty(BlockVine.UP, dir == Direction.UP);
    vine.withProperty(BlockVine.NORTH, dir == Direction.NORTH);
    vine.withProperty(BlockVine.EAST, dir == Direction.EAST);
    vine.withProperty(BlockVine.SOUTH, dir == Direction.SOUTH);
    vine.withProperty(BlockVine.WEST, dir == Direction.WEST);
    return vine;
  }
}
