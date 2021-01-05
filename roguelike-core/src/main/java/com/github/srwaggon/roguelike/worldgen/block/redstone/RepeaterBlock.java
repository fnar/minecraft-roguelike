package com.github.srwaggon.roguelike.worldgen.block.redstone;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class RepeaterBlock extends SingleBlockBrush {

  public enum Delay {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    ;

    int delay;

    Delay(int delay) {
      this.delay = delay;
    }

    public int getDelay() {
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

  public int getDelay() {
    return delay.getDelay();
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

}
