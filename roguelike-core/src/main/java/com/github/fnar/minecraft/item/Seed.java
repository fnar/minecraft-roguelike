package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.material.Crop;

public class Seed extends RldBaseItem {

  private final Crop crop;

  public Seed(Crop crop) {
    this.crop = crop;
  }

  public Crop getCrop() {
    return crop;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.SEED;
  }

}
