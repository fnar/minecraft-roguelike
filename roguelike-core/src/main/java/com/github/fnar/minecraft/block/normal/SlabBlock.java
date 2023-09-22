package com.github.fnar.minecraft.block.normal;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

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
    return new SlabBlock(BlockType.COBBLESTONE_SLAB);
  }

  public static SlabBlock darkOak() {
    return new SlabBlock(BlockType.DARK_OAK_SLAB);
  }

  public static SlabBlock jungle() {
    return new SlabBlock(BlockType.JUNGLE_SLAB);
  }

  public static SlabBlock legacyOak() {
    return new SlabBlock(BlockType.PETRIFIED_OAK_SLAB);
  }

  public static SlabBlock oak() {
    return new SlabBlock(BlockType.OAK_SLAB);
  }

  public static SlabBlock netherBrick() {
    return new SlabBlock(BlockType.NETHER_BRICK_SLAB);
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

  public static SlabBlock purpur() {
    return new SlabBlock(BlockType.PURPUR_SLAB);
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
    return new SlabBlock(BlockType.STONE_BRICK_SLAB);
  }

  public boolean isFullBlock() {
    return isFullBlock;
  }

  public SlabBlock setFullBlock() {
    return setFullBlock(true);
  }

  public SlabBlock setFullBlock(boolean fullBlock) {
    isFullBlock = fullBlock;
    return this;
  }

  public boolean isTop() {
    return isTop;
  }

  public SlabBlock setTop() {
    return setTop(true).setFullBlock(false);
  }

  public SlabBlock setTop(boolean top) {
    isTop = top;
    return this;
  }

  public SlabBlock setBottom() {
    return setTop(false).setFullBlock(false);
  }

  public boolean isSeamless() {
    return isSeamless;
  }

  public SlabBlock setSeamless(boolean seamless) {
    isSeamless = seamless;
    return this;
  }

  public SlabBlock withWaterlogging(boolean isWaterlogged) {
    super.withWaterlogging(isWaterlogged);
    return this;
  }

  public SlabBlock withWaterlogging() {
    return this.withWaterlogging(true);
  }

  @Override
  public SlabBlock copy() {
    SlabBlock copy = new SlabBlock(this.getBlockType());
    copy.setMaterial(getMaterial());
    copy.setFacing(getFacing());
    copy.setFullBlock(isFullBlock());
    copy.setSeamless(isSeamless());
    copy.setTop(isTop());
    copy.setWaterlogged(isWaterlogged());
    return copy;
  }
}
