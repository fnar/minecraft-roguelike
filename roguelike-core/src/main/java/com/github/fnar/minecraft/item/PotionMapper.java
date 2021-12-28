package com.github.fnar.minecraft.item;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public interface PotionMapper {
  ItemStack map(Potion potion);

  ItemPotion map(Potion.Form form);

  ItemStack map(RldItemStack rldItemStack);
}
