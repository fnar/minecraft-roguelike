package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.SlabBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.SlabType;

public class SlabBlockMapper1_14 {
  public static BlockState map(SlabBlock slabBlock) {
    return getBlock(slabBlock.getBlockType()).getDefaultState()
        .with(net.minecraft.block.SlabBlock.TYPE, mapType(slabBlock))
        .with(net.minecraft.block.SlabBlock.WATERLOGGED, slabBlock.isWaterlogged());
  }

  private static SlabType mapType(SlabBlock slabBlock) {
    return slabBlock.isFullBlock() ? SlabType.DOUBLE : slabBlock.isTop() ? SlabType.TOP : SlabType.BOTTOM;
  }

  private static Block getBlock(BlockType blockType) {
    switch (blockType) {
      case STONE_SLAB:
        return Blocks.STONE_SLAB;
      case SMOOTH_STONE_SLAB:
        return Blocks.SMOOTH_STONE_SLAB;
      case SANDSTONE_SLAB:
        return Blocks.SANDSTONE_SLAB;
      case PETRIFIED_OAK_SLAB:
        return Blocks.PETRIFIED_OAK_SLAB;
      case COBBLESTONE_SLAB:
        return Blocks.COBBLESTONE_SLAB;
      case BRICK_SLAB:
        return Blocks.BRICK_SLAB;
      case STONE_BRICK_SLAB:
        return Blocks.STONE_BRICK_SLAB;
      case NETHER_BRICK_SLAB:
        return Blocks.NETHER_BRICK_SLAB;
      case QUARTZ_SLAB:
        return Blocks.QUARTZ_SLAB;
      case RED_SANDSTONE:
        return Blocks.RED_SANDSTONE;
      case SMOOTH_RED_SANDSTONE_SLAB:
        return Blocks.SMOOTH_RED_SANDSTONE_SLAB;
      case OAK_SLAB:
        return Blocks.OAK_SLAB;
      case SPRUCE_SLAB:
        return Blocks.SPRUCE_SLAB;
      case BIRCH_SLAB:
        return Blocks.BIRCH_SLAB;
      case JUNGLE_SLAB:
        return Blocks.JUNGLE_SLAB;
      case ACACIA_SLAB:
        return Blocks.ACACIA_SLAB;
      case DARK_OAK_SLAB:
        return Blocks.DARK_OAK_SLAB;
      default:
        throw new IllegalStateException("Unexpected value: " + blockType);
    }
  }
}
