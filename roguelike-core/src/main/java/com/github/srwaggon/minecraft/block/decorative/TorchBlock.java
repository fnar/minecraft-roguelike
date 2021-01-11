package com.github.srwaggon.minecraft.block.decorative;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

public class TorchBlock extends SingleBlockBrush {

  private boolean isLit = true;

  public TorchBlock(BlockType blockType) {
    super(blockType);
  }

  public TorchBlock extinguish() {
    this.isLit = false;
    return this;
  }

  public boolean isLit() {
    return isLit;
  }

  public static TorchBlock redstone() {
    return new TorchBlock(BlockType.REDSTONE_TORCH);
  }

  public static TorchBlock torch() {
    return new TorchBlock(BlockType.TORCH);
  }
}
