package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.InfestedBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class InfestedBlockMapper1_14 {
  public static BlockState map(InfestedBlock block) {
    switch (block.getStone()) {
      case STONE:
        return Blocks.INFESTED_STONE.getDefaultState();
      case COBBLESTONE:
        return Blocks.INFESTED_COBBLESTONE.getDefaultState();
      case STONE_BRICKS:
        return Blocks.INFESTED_STONE_BRICKS.getDefaultState();
      case MOSSY_STONE_BRICKS:
        return Blocks.INFESTED_MOSSY_STONE_BRICKS.getDefaultState();
      case CRACKED_STONE_BRICKS:
        return Blocks.INFESTED_CRACKED_STONE_BRICKS.getDefaultState();
      case CHISELED_STONE_BRICKS:
        return Blocks.INFESTED_CHISELED_STONE_BRICKS.getDefaultState();
      default:
        throw new IllegalStateException("Unexpected value: " + block.getStone());
    }
  }
}
