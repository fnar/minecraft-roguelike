package com.github.fnar.minecraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;

import greymerk.roguelike.worldgen.Direction;

public class VineBlockMapper1_14 {
  static BlockState map(Direction dir) {
    BlockState vine = Blocks.VINE.getDefaultState();
    vine.with(VineBlock.UP, dir == Direction.UP);
    vine.with(VineBlock.NORTH, dir == Direction.NORTH);
    vine.with(VineBlock.EAST, dir == Direction.EAST);
    vine.with(VineBlock.SOUTH, dir == Direction.SOUTH);
    vine.with(VineBlock.WEST, dir == Direction.WEST);
    return vine;
  }
}
