package com.github.srwaggon.roguelike.worldgen.block.redstone;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

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
