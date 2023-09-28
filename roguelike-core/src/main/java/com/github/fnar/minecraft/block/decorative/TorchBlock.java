package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.worldgen.Direction;

public class TorchBlock extends SingleBlockBrush {

  private boolean isLit = true;

  public TorchBlock(BlockType blockType) {
    super(blockType);
    setFacing(Direction.UP);
  }

  public boolean isAttachedToWall() {
    return getFacing().isCardinal();
  }

  public TorchBlock extinguish() {
    this.isLit = false;
    return this;
  }

  public boolean isLit() {
    return isLit;
  }

  public TorchBlock setLit(boolean isLit) {
    this.isLit = isLit;
    return this;
  }

  public boolean isRedstone() {
    return getBlockType().equals(BlockType.REDSTONE_TORCH);
  }

  public static TorchBlock redstone() {
    return new TorchBlock(BlockType.REDSTONE_TORCH);
  }

  public static TorchBlock torch() {
    return new TorchBlock(BlockType.TORCH);
  }

  @Override
  public TorchBlock copy() {
    TorchBlock copy = new TorchBlock(getBlockType());
    copy.setFacing(getFacing());
    copy.setLit(isLit);
    return copy;
  }
}
