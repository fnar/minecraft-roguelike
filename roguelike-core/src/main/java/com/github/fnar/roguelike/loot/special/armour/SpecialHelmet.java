package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialHelmet extends SpecialArmour {

  public SpecialHelmet(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getItem());
    withName(getSpecialHelmetName());
    withHelmetEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColour(random);
  }

  private void withHelmetEnchantments(Random random) {
    if (random.nextInt(20) == 0) {
      withDiving(random);
    } else {
      withArmorEnchantments(random);
    }
  }

  private void withDiving(Random random) {
    withEnchantment(Enchantment.Effect.RESPIRATION.atLevel(3));
    withEnchantment(Enchantment.Effect.AQUA_AFFINITY);
    withEnchantment(Enchantment.Effect.PROTECTION.atLevel(SpecialArmour.getProtectionLevel(getQuality(), random)));
    withSuffix("of Diving");
  }

  private RldItem getItem() {
    return ArmourType.HELMET.asItem().withQuality(getQuality());
  }

  private String getSpecialHelmetName() {
    switch (getQuality()) {
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
