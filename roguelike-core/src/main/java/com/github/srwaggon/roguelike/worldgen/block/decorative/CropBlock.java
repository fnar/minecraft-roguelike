package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class CropBlock extends SingleBlockBrush {

  private Crop crop;

  public CropBlock() {
    super(BlockType.CROP);
  }

  public CropBlock setCrop(Crop crop) {
    this.crop = crop;
    return this;
  }

  public Crop getCrop() {
    return crop;
  }

  public static CropBlock crop() {
    return new CropBlock();
  }
}
