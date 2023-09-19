package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Direction;

public class WoodMapper1_12 {

  public static IBlockState mapLogs(Wood type, Direction facing) {
    Block block = mapLogBlock(type);
    return block.getDefaultState()
        .withProperty(mapLogVariantProperty(type), getWoodVariant(type))
        .withProperty(BlockLog.LOG_AXIS, mapLogFacing(facing));
  }

  private static BlockLog.EnumAxis mapLogFacing(Direction facing) {
    switch (facing) {
      case UP:
      case DOWN:
        return BlockLog.EnumAxis.Y;
      case EAST:
      case WEST:
        return BlockLog.EnumAxis.X;
      case NORTH:
      case SOUTH:
        return BlockLog.EnumAxis.Z;
      default:
        return BlockLog.EnumAxis.NONE;
    }
  }

  private static Block mapLogBlock(Wood type) {
    switch (type) {
      case OAK:
      case JUNGLE:
      case BIRCH:
      case SPRUCE:
      default:
        return Blocks.LOG;
      case ACACIA:
      case DARK_OAK:
        return Blocks.LOG2;
    }
  }

  private static PropertyEnum<BlockPlanks.EnumType> mapLogVariantProperty(Wood wood) {
    switch (wood) {
      default:
      case OAK:
      case SPRUCE:
      case JUNGLE:
      case BIRCH:
        return BlockOldLog.VARIANT;
      case ACACIA:
      case DARK_OAK:
        return BlockNewLog.VARIANT;
    }
  }

  public static IBlockState mapPlanks(Wood type) {
    return Blocks.PLANKS.getDefaultState()
        .withProperty(BlockPlanks.VARIANT, getWoodVariant(type));
  }

  public static IBlockState mapLeaves(Wood type) {
    Block base = getLeavesBlock(type);
    return base.getDefaultState()
        .withProperty(getLeavesVariantProperty(base), getWoodVariant(type))
        .withProperty(BlockLeaves.DECAYABLE, false);
  }

  private static Block getLeavesBlock(Wood type) {
    switch (type) {
      case OAK:
      case SPRUCE:
      case JUNGLE:
      case BIRCH:
        return Blocks.LEAVES;
      case ACACIA:
      case DARK_OAK:
        return Blocks.LEAVES2;
      default:
        return Blocks.LOG;
    }
  }

  private static BlockPlanks.EnumType getWoodVariant(Wood type) {
    switch (type) {
      case OAK:
      default:
        return BlockPlanks.EnumType.OAK;
      case SPRUCE:
        return BlockPlanks.EnumType.SPRUCE;
      case BIRCH:
        return BlockPlanks.EnumType.BIRCH;
      case JUNGLE:
        return BlockPlanks.EnumType.JUNGLE;
      case ACACIA:
        return BlockPlanks.EnumType.ACACIA;
      case DARK_OAK:
        return BlockPlanks.EnumType.DARK_OAK;
    }
  }

  private static PropertyEnum<BlockPlanks.EnumType> getLeavesVariantProperty(Block base) {
    return base == Blocks.LEAVES ? BlockOldLeaf.VARIANT : BlockNewLeaf.VARIANT;
  }
}
