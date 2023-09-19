package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class SaplingBlockMapper1_12 {

  public static IBlockState map(Wood type) {
    return Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, mapSaplingType(type));
  }

  private static BlockPlanks.EnumType mapSaplingType(Wood type) {
    switch (type) {
      default:
      case OAK:
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
}
