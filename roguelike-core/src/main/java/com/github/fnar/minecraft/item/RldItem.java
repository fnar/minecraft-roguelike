package com.github.fnar.minecraft.item;

public interface RldItem {

  default RldItemStack asItemStack() {
    return new RldItemStack(this);
  }

  ItemType getItemType();

}
