package com.github.fnar.roguelike.loot.special.armour;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.Quality.*;

public class SpecialLeggings extends SpecialArmour {

  public SpecialLeggings(Random random, int level) {
    this(random, rollRandomQualityByLevel(random, level, Equipment.LEGS));
  }

  public SpecialLeggings(Random random, Quality quality) {
    withQuality(quality);
    withItem(getSpecialLeggingsItem());
    withName(getSpecialLeggingsName());
    withLeggingsEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColourIfWood();
  }

  private void withLeggingsEnchantments(Random random) {
    withArmorEnchantments(random);
  }

  private Item getSpecialLeggingsItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_LEGGINGS;
      case GOLD:
        return Items.GOLDEN_LEGGINGS;
      case IRON:
        return Items.IRON_LEGGINGS;
      case STONE:
        return Items.CHAINMAIL_LEGGINGS;
      case WOOD:
      default:
        return Items.LEATHER_LEGGINGS;
    }
  }

  private String getSpecialLeggingsName() {
    switch (quality) {
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
