package com.github.fnar.minecraft.item;

import net.minecraft.item.ItemStack;

public interface RecordMapper {
  ItemStack map(RldItemStack rldItemStack);
}
