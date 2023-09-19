package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.material.Wood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class LeavesBlockMapper1_12 {

  public static IBlockState map(Wood type, boolean decay) {
    Block base = getBlockId(type);
    return base.getDefaultState()
        .withProperty(getVariantType(base), BlockMapper1_12.getWoodVariant(type))
        .withProperty(BlockLeaves.DECAYABLE, decay);
  }

  private static Block getBlockId(Wood type) {
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

  private static PropertyEnum<BlockPlanks.EnumType> getVariantType(Block base) {
    return base == Blocks.LEAVES ? BlockOldLeaf.VARIANT : BlockNewLeaf.VARIANT;
  }

}
