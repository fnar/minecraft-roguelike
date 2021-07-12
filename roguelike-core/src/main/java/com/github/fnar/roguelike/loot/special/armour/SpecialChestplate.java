package com.github.fnar.roguelike.loot.special.armour;

import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.Quality.*;

public class SpecialChestplate extends SpecialArmour {

  public SpecialChestplate(Random random, int level) {
    this(random, rollRandomQualityByLevel(random, level, Equipment.CHEST));
  }

  public SpecialChestplate(Random random, Quality quality) {
    withQuality(quality);
    withItem(getChestplateItem());
    withName(getChestplateName());
    withChestplateEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColourIfWood();
  }

  private void withChestplateEnchantments(Random random) {
    withArmorEnchantments(random);
  }

  private ItemArmor getChestplateItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_CHESTPLATE;
      case GOLD:
        return Items.GOLDEN_CHESTPLATE;
      case IRON:
        return Items.IRON_CHESTPLATE;
      case STONE:
        return Items.CHAINMAIL_CHESTPLATE;
      case WOOD:
      default:
        return Items.LEATHER_CHESTPLATE;
    }
  }

  private String getChestplateName() {
    switch (quality) {
      case DIAMOND:
        return "Plate";
      case GOLD:
      case IRON:
        return "Cuirass";
      case STONE:
        return "Hauberk";
      case WOOD:
      default:
        return "Tunic";
    }
  }

}
