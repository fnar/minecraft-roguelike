package com.github.fnar.minecraft.item;

import com.github.fnar.minecraft.material.Wood;

public class Sapling extends RldBaseItem {

  private final Wood wood;

  public Sapling(Wood wood) {
    this.wood = wood;
  }

  public Wood getWood() {
    return wood;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.SAPLING;
  }

}
