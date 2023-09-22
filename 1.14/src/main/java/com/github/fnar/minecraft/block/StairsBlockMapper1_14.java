package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.normal.StairsBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;

public class StairsBlockMapper1_14 {

  public static BlockState mapStairsToState(StairsBlock stairsBlock) {
    JsonElement json = stairsBlock.getJson();
    BlockState blockState = json != null
        ? BlockParser1_14.parse(json)
        : getBlockForStairs(stairsBlock.getBlockType()).getDefaultState();
    return blockState
        .with(net.minecraft.block.StairsBlock.FACING, DirectionMapper1_14.map(stairsBlock.getFacing()))
        .with(net.minecraft.block.StairsBlock.HALF, stairsBlock.isUpsideDown() ? Half.TOP : Half.BOTTOM)
        .with(net.minecraft.block.StairsBlock.SHAPE, StairsShape.STRAIGHT) // TODO: implement
        .with(net.minecraft.block.StairsBlock.WATERLOGGED, stairsBlock.isWaterlogged())
        ;
  }

  private static Block getBlockForStairs(BlockType stairs) {
    switch (stairs) {
      case ACACIA_STAIRS:
        return Blocks.ACACIA_STAIRS;
      case BIRCH_STAIRS:
        return Blocks.BIRCH_STAIRS;
      case BRICK_STAIRS:
        return Blocks.BRICK_STAIRS;
      case DARK_OAK_STAIRS:
        return Blocks.DARK_OAK_STAIRS;
      case JUNGLE_STAIRS:
        return Blocks.JUNGLE_STAIRS;
      case NETHER_BRICK_STAIRS:
        return Blocks.NETHER_BRICK_STAIRS;
      case OAK_STAIRS:
        return Blocks.OAK_STAIRS;
      case PURPUR_STAIRS:
        return Blocks.PURPUR_STAIRS;
      case QUARTZ_STAIRS:
        return Blocks.QUARTZ_STAIRS;
      case RED_SANDSTONE_STAIRS:
        return Blocks.RED_SANDSTONE_STAIRS;
      case SANDSTONE_STAIRS:
        return Blocks.SANDSTONE_STAIRS;
      case SPRUCE_STAIRS:
        return Blocks.SPRUCE_STAIRS;
      case STONE_BRICK_STAIRS:
        return Blocks.STONE_BRICK_STAIRS;
      case STONE_STAIRS:
      default:
        return Blocks.STONE_STAIRS;
    }
  }
}
