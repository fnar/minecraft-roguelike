package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

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
