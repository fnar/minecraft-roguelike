package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;

import greymerk.roguelike.worldgen.Direction;

public class WoodMapper1_14 {

  public static BlockState mapLogs(Wood type, Direction facing) {
    return mapLogBlock(type)
        .getDefaultState()
        .with(LogBlock.AXIS, mapLogFacing(facing));
  }

  private static net.minecraft.util.Direction.Axis mapLogFacing(Direction facing) {
    switch (facing) {
      case UP:
      case DOWN:
        return net.minecraft.util.Direction.Axis.Y;
      case EAST:
      case WEST:
        return net.minecraft.util.Direction.Axis.X;
      case NORTH:
      case SOUTH:
        return net.minecraft.util.Direction.Axis.Z;
      default:
        throw new IllegalStateException("Unexpected value: " + facing);
    }
  }

  private static Block mapLogBlock(Wood type) {
    switch (type) {
      default:
      case OAK:
        return Blocks.OAK_LOG;
      case JUNGLE:
        return Blocks.JUNGLE_LOG;
      case BIRCH:
        return Blocks.BIRCH_LOG;
      case SPRUCE:
        return Blocks.SPRUCE_LOG;
      case ACACIA:
        return Blocks.ACACIA_LOG;
      case DARK_OAK:
        return Blocks.DARK_OAK_LOG;
    }
  }

  public static BlockState mapPlanks(Wood type) {
    return mapPlanksBlock(type)
        .getDefaultState();
  }

  private static Block mapPlanksBlock(Wood type) {
    switch (type) {
      default:
      case OAK:
        return Blocks.OAK_PLANKS;
      case JUNGLE:
        return Blocks.JUNGLE_PLANKS;
      case BIRCH:
        return Blocks.BIRCH_PLANKS;
      case SPRUCE:
        return Blocks.SPRUCE_PLANKS;
      case ACACIA:
        return Blocks.ACACIA_PLANKS;
      case DARK_OAK:
        return Blocks.DARK_OAK_PLANKS;
    }
  }

  public static BlockState mapLeaves(Wood type) {
    return getLeavesBlock(type)
        .getDefaultState()
        .with(LeavesBlock.PERSISTENT, true);
  }

  private static Block getLeavesBlock(Wood type) {
    switch (type) {
      case OAK:
        return Blocks.OAK_LEAVES;
      case SPRUCE:
        return Blocks.SPRUCE_LEAVES;
      case JUNGLE:
        return Blocks.JUNGLE_LEAVES;
      case BIRCH:
        return Blocks.BIRCH_LEAVES;
      case ACACIA:
        return Blocks.ACACIA_LEAVES;
      case DARK_OAK:
        return Blocks.DARK_OAK_LEAVES;
      default:
        throw new IllegalStateException("Unexpected value: " + type);
    }
  }

  public static BlockState map(Wood type) {
    return mapSaplingType(type).getDefaultState();
  }

  private static Block mapSaplingType(Wood type) {
    switch (type) {
      default:
      case OAK:
        return Blocks.OAK_SAPLING;
      case SPRUCE:
        return Blocks.SPRUCE_SAPLING;
      case BIRCH:
        return Blocks.BIRCH_SAPLING;
      case JUNGLE:
        return Blocks.JUNGLE_SAPLING;
      case ACACIA:
        return Blocks.ACACIA_SAPLING;
      case DARK_OAK:
        return Blocks.DARK_OAK_SAPLING;
    }
  }
}
