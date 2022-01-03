package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.provider.ArmourQualityOddsTable.rollArmourQuality;

public class SpecialHelmet extends SpecialArmour {

  public SpecialHelmet(Random random, int level) {
    this(random, rollArmourQuality(random, level));
  }

  public SpecialHelmet(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getItem());
    withName(getSpecialHelmetName());
    withHelmetEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColourIfWood();
  }

  private void withHelmetEnchantments(Random random) {
    if (random.nextInt(20) == 0) {
      withDiving(random);
    } else {
      withArmorEnchantments(random);
    }
  }

  private void withDiving(Random random) {
    withEnchantment(Enchantment.Effect.RESPIRATION, 3);
    withEnchantment(Enchantment.Effect.AQUA_AFFINITY, 1);
    withEnchantment(Enchantment.Effect.PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Diving");
  }

  private RldItem getItem() {
    return ArmourType.HELMET.asItem().withQuality(quality);
  }

  private String getSpecialHelmetName() {
    switch (quality) {
      case DIAMOND:
        return "Helm";
      case GOLD:
        return "Crown";
      case IRON:
        return "Sallet";
      case STONE:
        return "Coif";
      case WOOD:
      default:
        return "Skullcap";
    }
  }
}
