package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.SlabBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class SlabBlockMapper1_12 {
  public static IBlockState map(SlabBlock slabBlock) {
    Block minecraftBlock = getBlock(slabBlock.getBlockType(), slabBlock.isFullBlock());
    IBlockState blockState = minecraftBlock.getDefaultState();
    switch (slabBlock.getBlockType()) {
      case STONE_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE);
        break;
      case SANDSTONE_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        break;
      case PETRIFIED_OAK_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.WOOD);
        break;
      case COBBLESTONE_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE);
        break;
      case BRICK_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK);
        break;
      case STONE_BRICK_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
        break;
      case NETHER_BRICK_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK);
        break;
      case QUARTZ_SLAB:
        blockState.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ);
        break;
      case RED_SANDSTONE_SLAB:
      case SMOOTH_RED_SANDSTONE_SLAB:
        blockState.withProperty(BlockStoneSlabNew.VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE);
        break;
      case OAK_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.ACACIA);
        break;
      case DARK_OAK_SLAB:
        blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        break;
      case PURPUR_SLAB:
        blockState.withProperty(BlockPurpurSlab.VARIANT, BlockPurpurSlab.Variant.DEFAULT);
        break;
      default:
    }

    if (!slabBlock.isFullBlock() && slabBlock.isTop()) {
      blockState.withProperty(BlockWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
    }

    if (slabBlock.isFullBlock() && slabBlock.isSeamless()) {
      blockState.withProperty(BlockStoneSlab.SEAMLESS, true);
    }

    return blockState;
  }

  private static Block getBlock(BlockType blockType, boolean isFullBlock) {
    switch (blockType) {
      case STONE_SLAB:
      case SANDSTONE_SLAB:
      case PETRIFIED_OAK_SLAB:
      case COBBLESTONE_SLAB:
      case BRICK_SLAB:
      case STONE_BRICK_SLAB:
      case NETHER_BRICK_SLAB:
      case QUARTZ_SLAB:
        return isFullBlock ? Blocks.DOUBLE_STONE_SLAB : Blocks.STONE_SLAB;
      case RED_SANDSTONE:
      case SMOOTH_RED_SANDSTONE_SLAB:
        return isFullBlock ? Blocks.DOUBLE_STONE_SLAB2 : Blocks.STONE_SLAB2;
      case OAK_SLAB:
      case SPRUCE_SLAB:
      case BIRCH_SLAB:
      case JUNGLE_SLAB:
      case ACACIA_SLAB:
      case DARK_OAK_SLAB:
        return isFullBlock ? Blocks.DOUBLE_WOODEN_SLAB : Blocks.WOODEN_SLAB;
      default:
        return Blocks.STONE_SLAB;
    }
  }
}
