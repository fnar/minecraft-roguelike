package com.github.fnar.minecraft.block.redstone;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

public class RepeaterBlock extends SingleBlockBrush {

  public enum Delay {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    ;

    private final int delay;

    Delay(int delay) {
      this.delay = delay;
    }

    public int asInt() {
      return delay;
    }
  }

  private boolean isPowered;
  private Delay delay = Delay.ONE;

  public RepeaterBlock() {
    super(BlockType.REPEATER);
  }

  public RepeaterBlock setDelay(Delay delay) {
    this.delay = delay;
    return this;
  }

  public Delay getDelay() {
    return delay;
  }

  public RepeaterBlock setPowered(boolean isPowered) {
    this.isPowered = isPowered;
    return this;
  }

  public boolean isPowered() {
    return isPowered;
  }

  public static RepeaterBlock repeater() {
    return new RepeaterBlock();
  }

  @Override
  public RepeaterBlock copy() {
    RepeaterBlock copy = new RepeaterBlock();
    copy.setFacing(getFacing());
    copy.setPowered(isPowered);
    copy.setDelay(delay);
    return copy;
  }
}
