package com.github.srwaggon.minecraft.block.decorative;

public enum Crop {

  BEETROOTS,
  CARROTS,
  COCOA,
  MELON_STEM,
  NETHER_WART,
  POTATOES,
  PUMPKIN_STEM,
  WHEAT,
  ;

  public CropBlock getBrush() {
    return CropBlock.crop().setCrop(this);
  }

}
