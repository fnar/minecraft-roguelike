package com.github.srwaggon.roguelike.worldgen.block.normal;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class SlabBlock extends SingleBlockBrush {

  private boolean isFullBlock;
  private boolean isTop;
  private boolean isSeamless;

  public SlabBlock(BlockType blockType) {
    super(blockType);
  }

  public static SlabBlock acacia() {
    return new SlabBlock(BlockType.ACACIA_SLAB);
  }

  public static SlabBlock birch() {
    return new SlabBlock(BlockType.BIRCH_SLAB);
  }

  public static SlabBlock brick() {
    return new SlabBlock(BlockType.BRICK_SLAB);
  }

  public static SlabBlock cobble() {
    return new SlabBlock(BlockType.COBBLE_SLAB);
  }

  public static SlabBlock darkOak() {
    return new SlabBlock(BlockType.DARK_OAK_SLAB);
  }

  public static SlabBlock jungle() {
    return new SlabBlock(BlockType.JUNGLE_SLAB);
  }

  public static SlabBlock legacyOak() {
    return new SlabBlock(BlockType.LEGACY_OAK_SLAB);
  }

  public static SlabBlock oak() {
    return new SlabBlock(BlockType.OAK_SLAB);
  }

  public static SlabBlock netherBrick() {
    return new SlabBlock(BlockType.NETHERBRICK_SLAB);
  }

  public static SlabBlock spruce() {
    return new SlabBlock(BlockType.SPRUCE_SLAB);
  }

  public static SlabBlock quartz() {
    return new SlabBlock(BlockType.QUARTZ_SLAB);
  }

  public static SlabBlock redSandStone() {
    return new SlabBlock(BlockType.RED_SANDSTONE_SLAB);
  }

  public static SlabBlock smoothRedSandstone() {
    return new SlabBlock(BlockType.SMOOTH_RED_SANDSTONE_SLAB);
  }

  public static SlabBlock sandstone() {
    return new SlabBlock(BlockType.SANDSTONE_SLAB);
  }

  public static SlabBlock stone() {
    return new SlabBlock(BlockType.STONE_SLAB);
  }

  public static SlabBlock stoneBrick() {
    return new SlabBlock(BlockType.STONEBRICK_SLAB);
  }

  public boolean isFullBlock() {
    return isFullBlock;
  }

  public SlabBlock setFullBlock(boolean fullBlock) {
    isFullBlock = fullBlock;
    return this;
  }

  public boolean isTop() {
    return isTop;
  }

  public SlabBlock setTop(boolean top) {
    isTop = top;
    return this;
  }

  public boolean isSeamless() {
    return isSeamless;
  }

  public SlabBlock setSeamless(boolean seamless) {
    isSeamless = seamless;
    return this;
  }
}
