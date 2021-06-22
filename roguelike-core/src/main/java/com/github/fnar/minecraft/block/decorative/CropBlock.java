package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

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
