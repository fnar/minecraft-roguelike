package com.github.srwaggon.minecraft.block.redstone;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

public class ComparatorBlock extends SingleBlockBrush {

  public enum Mode {
    COMPARE,
    SUBTRACTION
  }

  private Mode mode = Mode.COMPARE;
  private boolean isPowered;

  public ComparatorBlock() {
    super(BlockType.COMPARATOR);
  }

  public ComparatorBlock setMode(Mode mode) {
    this.mode = mode;
    return this;
  }

  public Mode getMode() {
    return mode;
  }

  public ComparatorBlock setPowered(boolean isPowered) {
    this.isPowered = isPowered;
    return this;
  }

  public boolean isPowered() {
    return isPowered;
  }

  public static ComparatorBlock comparator() {
    return new ComparatorBlock();
  }

}
