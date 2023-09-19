package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.SlabBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class SlabBlockMapper1_12 {
  public static IBlockState map(SlabBlock slabBlock) {
    Block minecraftBlock = getBlock(slabBlock.getBlockType(), slabBlock.isFullBlock());
    IBlockState metaBlock = minecraftBlock.getDefaultState();
    switch (slabBlock.getBlockType()) {
      case STONE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE);
        break;
      case SANDSTONE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        break;
      case LEGACY_OAK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.WOOD);
        break;
      case COBBLE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE);
        break;
      case BRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK);
        break;
      case STONEBRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
        break;
      case NETHERBRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK);
        break;
      case QUARTZ_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ);
        break;
      case RED_SANDSTONE_SLAB:
      case SMOOTH_RED_SANDSTONE_SLAB:
        metaBlock.withProperty(BlockStoneSlabNew.VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE);
        break;
      case OAK_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.ACACIA);
        break;
      case DARK_OAK_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        break;
      default:
    }

    if (!slabBlock.isFullBlock() && slabBlock.isTop()) {
      metaBlock.withProperty(BlockWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
    }

    if (slabBlock.isFullBlock() && slabBlock.isSeamless()) {
      metaBlock.withProperty(BlockStoneSlab.SEAMLESS, true);
    }

    return metaBlock;
  }

  private static Block getBlock(BlockType blockType, boolean isFullBlock) {
    switch (blockType) {
      case STONE_SLAB:
      case SANDSTONE_SLAB:
      case LEGACY_OAK_SLAB:
      case COBBLE_SLAB:
      case BRICK_SLAB:
      case STONEBRICK_SLAB:
      case NETHERBRICK_SLAB:
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
