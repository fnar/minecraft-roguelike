package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItem;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialLeggings extends SpecialArmour {

  public SpecialLeggings(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getSpecialLeggingsItem());
    withName(getSpecialLeggingsName());
    withLeggingsEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColour(random);
  }

  private void withLeggingsEnchantments(Random random) {
    withArmorEnchantments(random);
  }

  private RldItem getSpecialLeggingsItem() {
    return ArmourType.LEGGINGS.asItem().withQuality(getQuality());
  }

  private String getSpecialLeggingsName() {
    switch (getQuality()) {
      case DIAMOND:
        return "Leggings";
      case GOLD:
      case IRON:
        return "Leg-plates";
      case STONE:
        return "Chausses";
      case WOOD:
      default:
        return "Pantaloons";
    }
  }
}
