package com.github.fnar.minecraft.material;

import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.item.Seed;

import java.util.Random;

public enum Crop {

  BEETROOTS,
  CARROTS,
  COCOA,
  MELON,
  NETHER_WART,
  POTATOES,
  PUMPKIN,
  WHEAT,
  ;

  public Seed asSeed() {
    return new Seed(this);
  }

  public CropBlock getBrush() {
    return CropBlock.crop().setCrop(this);
  }

  public static Crop chooseOneAtRandom(Random random) {
    Crop[] crops = Crop.values();
    return crops[random.nextInt(crops.length)];
  }

}
