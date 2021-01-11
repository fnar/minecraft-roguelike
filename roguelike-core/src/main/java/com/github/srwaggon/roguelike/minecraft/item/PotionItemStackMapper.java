package com.github.srwaggon.roguelike.minecraft.item;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.PotionType;

public class PotionItemStackMapper {
  public static ItemStack map(Potion potion) {
    return PotionType.getSpecific(potion.getForm(), potion.getType(), potion.isAmplified(), potion.isExtended());
  }
}
