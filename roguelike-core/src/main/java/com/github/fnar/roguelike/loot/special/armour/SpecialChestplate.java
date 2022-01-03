package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItem;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.provider.ArmourQualityOddsTable.rollArmourQuality;

public class SpecialChestplate extends SpecialArmour {

  public SpecialChestplate(Random random, int level) {
    this(random, rollArmourQuality(random, level));
  }

  public SpecialChestplate(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getItem());
    withName(getChestplateName());
    withChestplateEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColourIfWood();
  }

  private void withChestplateEnchantments(Random random) {
    withArmorEnchantments(random);
  }

  private RldItem getItem() {
    return ArmourType.CHESTPLATE.asItem().withQuality(quality);
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
