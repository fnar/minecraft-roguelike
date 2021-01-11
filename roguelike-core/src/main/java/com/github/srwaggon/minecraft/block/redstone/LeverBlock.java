package com.github.srwaggon.minecraft.block.redstone;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

public class LeverBlock extends SingleBlockBrush {

  private boolean isActive;

  public LeverBlock() {
    super(BlockType.LEVER);
  }

  public LeverBlock setActive(boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public static LeverBlock lever() {
    return new LeverBlock();
  }

  public boolean isActive() {
    return isActive;
  }
}
