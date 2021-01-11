package com.github.srwaggon.minecraft.block.normal;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Direction;

public enum Wood {

  OAK,
  SPRUCE,
  BIRCH,
  JUNGLE,
  ACACIA,
  DARK_OAK;

  private BlockType getLeavesBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_LEAVES;
      case SPRUCE:
        return BlockType.SPRUCE_LEAVES;
      case BIRCH:
        return BlockType.BIRCH_LEAVES;
      case JUNGLE:
        return BlockType.JUNGLE_LEAVES;
      case ACACIA:
        return BlockType.ACACIA_LEAVES2;
      case DARK_OAK:
        return BlockType.DARK_OAK_LEAVES;
    }
  }

  public SingleBlockBrush getLeaves() {
    return getLeavesBlockType().getBrush();
  }

  public BlockType getPlanksBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_PLANK;
      case SPRUCE:
        return BlockType.SPRUCE_PLANK;
      case BIRCH:
        return BlockType.BIRCH_PLANK;
      case JUNGLE:
        return BlockType.JUNGLE_PLANK;
      case ACACIA:
        return BlockType.ACACIA_PLANK;
      case DARK_OAK:
        return BlockType.DARK_OAK_PLANK;
    }
  }

  public SingleBlockBrush getPlanks() {
    return getPlanksBlockType().getBrush();
  }

  private BlockType getLogBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_LOG;
      case SPRUCE:
        return BlockType.SPRUCE_LOG;
      case BIRCH:
        return BlockType.BIRCH_LOG;
      case JUNGLE:
        return BlockType.JUNGLE_LOG;
      case ACACIA:
        return BlockType.ACACIA_LOG;
      case DARK_OAK:
        return BlockType.DARK_OAK_LOG;
    }
  }

  public BlockBrush getLog() {
    return getLogBlockType().getBrush().setFacing(Direction.UP);
  }

  private BlockType getFenceBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_FENCE;
      case SPRUCE:
        return BlockType.SPRUCE_FENCE;
      case BIRCH:
        return BlockType.BIRCH_FENCE;
      case JUNGLE:
        return BlockType.JUNGLE_FENCE;
      case ACACIA:
        return BlockType.ACACIA_FENCE;
      case DARK_OAK:
        return BlockType.DARK_OAK_FENCE;
    }
  }

  public SingleBlockBrush getFence() {
    return getFenceBlockType().getBrush();
  }

  private BlockType getStairsBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_STAIRS;
      case SPRUCE:
        return BlockType.SPRUCE_STAIRS;
      case BIRCH:
        return BlockType.BIRCH_STAIRS;
      case JUNGLE:
        return BlockType.JUNGLE_STAIRS;
      case ACACIA:
        return BlockType.ACACIA_STAIRS;
      case DARK_OAK:
        return BlockType.DARK_OAK_STAIRS;
    }
  }

  public StairsBlock getStairs() {
    switch (this) {
      default:
      case OAK:
        return StairsBlock.oak();
      case SPRUCE:
        return StairsBlock.spruce();
      case BIRCH:
        return StairsBlock.birch();
      case JUNGLE:
        return StairsBlock.jungle();
      case ACACIA:
        return StairsBlock.acacia();
      case DARK_OAK:
        return StairsBlock.darkOak();
    }
  }

  public BlockType getSlabsBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_SLAB;
      case SPRUCE:
        return BlockType.SPRUCE_SLAB;
      case BIRCH:
        return BlockType.BIRCH_SLAB;
      case JUNGLE:
        return BlockType.JUNGLE_SLAB;
      case ACACIA:
        return BlockType.ACACIA_SLAB;
      case DARK_OAK:
        return BlockType.DARK_OAK_SLAB;
    }
  }

  public SlabBlock getSlabs() {
    switch (this) {
      default:
      case OAK:
        return SlabBlock.oak();
      case SPRUCE:
        return SlabBlock.spruce();
      case BIRCH:
        return SlabBlock.birch();
      case JUNGLE:
        return SlabBlock.jungle();
      case ACACIA:
        return SlabBlock.acacia();
      case DARK_OAK:
        return SlabBlock.darkOak();
    }
  }

  public DoorBlock getDoor() {
    switch (this) {
      default:
      case OAK:
        return DoorBlock.oak();
      case SPRUCE:
        return DoorBlock.spruce();
      case BIRCH:
        return DoorBlock.birch();
      case JUNGLE:
        return DoorBlock.jungle();
      case ACACIA:
        return DoorBlock.acacia();
      case DARK_OAK:
        return DoorBlock.darkOak();
    }
  }

  public BlockType getSaplingBlockType() {
    switch (this) {
      default:
      case OAK:
        return BlockType.OAK_SAPLING;
      case SPRUCE:
        return BlockType.SPRUCE_SAPLING;
      case BIRCH:
        return BlockType.BIRCH_SAPLING;
      case JUNGLE:
        return BlockType.JUNGLE_SAPLING;
      case ACACIA:
        return BlockType.ACACIA_SAPLING;
      case DARK_OAK:
        return BlockType.DARK_OAK_SAPLING;
    }
  }

  public SingleBlockBrush getSapling() {
    return getSaplingBlockType().getBrush();
  }
}
