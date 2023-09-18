package com.github.fnar.minecraft.item;

import net.minecraft.item.ItemStack;

public interface ItemMapper {
  ItemStack map(RldItemStack rldItemStack);
}
