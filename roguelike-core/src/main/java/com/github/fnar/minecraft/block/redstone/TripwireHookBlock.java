package com.github.fnar.minecraft.block.redstone;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

public class TripwireHookBlock extends SingleBlockBrush {



  private boolean isAttached = false;

  public TripwireHookBlock() {
    super(BlockType.TRIPWIRE_HOOK);
  }

  public boolean isAttached() {
    return isAttached;
  }

  public void setAttached(boolean attached) {
    isAttached = attached;
  }

  public TripwireHookBlock withIsAttached(boolean isAttached) {
    this.isAttached = isAttached;
    return this;
  }

}
