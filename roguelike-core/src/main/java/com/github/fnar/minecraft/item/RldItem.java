package com.github.fnar.minecraft.item;

public interface RldItem {

  default RldItemStack asStack() {
    return new RldItemStack(this, 1);
  }

  ItemType getItemType();

}
