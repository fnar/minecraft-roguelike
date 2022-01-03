package com.github.fnar.minecraft.item;

import greymerk.roguelike.util.DyeColor;

public class Dye extends RldBaseItem {

  private DyeColor color;

  public Dye(DyeColor color) {
    this.color = color;
  }

  public DyeColor getDyeColor() {
    return color;
  }

  public void setDyedColor(DyeColor dyeColor) {
    this.color = dyeColor;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.DYE;
  }
}
