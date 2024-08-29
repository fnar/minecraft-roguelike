package com.github.fnar.minecraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;

import greymerk.roguelike.worldgen.Direction;

public class VineBlockMapper1_14 {
  static BlockState map(Direction dir) {
    return Blocks.VINE.getDefaultState()
        .with(VineBlock.UP, dir == Direction.UP)
        .with(VineBlock.NORTH, dir == Direction.NORTH)
        .with(VineBlock.EAST, dir == Direction.EAST)
        .with(VineBlock.SOUTH, dir == Direction.SOUTH)
        .with(VineBlock.WEST, dir == Direction.WEST);
  }
}
