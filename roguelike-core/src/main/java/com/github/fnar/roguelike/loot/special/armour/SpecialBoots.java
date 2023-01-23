package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.Quality.DIAMOND;

public class SpecialBoots extends SpecialArmour {

  public SpecialBoots(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getItem());
    withBootsEnchantments(random);
    withCommonEnchantments(random);
    withName(getSpecialBootsName());
    withArmourPrefix();
    withRandomColour(random);
  }

  private void withBootsEnchantments(Random random) {
    if (random.nextInt(20) == 0) {
      withFeatherFalling(random);
    } else {
      withArmorEnchantments(random);
    }
  }

  private void withFeatherFalling(Random random) {
    int level = getQuality() == DIAMOND ? 4 : 1 + random.nextInt(3);
    withEnchantment(Enchantment.Effect.FEATHER_FALLING.atLevel(level));
    withEnchantment(Enchantment.Effect.PROTECTION.atLevel(SpecialArmour.getProtectionLevel(getQuality(), random)));
    withSuffix("of Lightness");
  }

  private RldItem getItem() {
    return ArmourType.BOOTS.asItem().withQuality(getQuality());
  }

  private String getSpecialBootsName() {
    switch (getQuality()) {
      case DIAMOND:
        return "Boots";
      case GOLD:
      case IRON:
        return "Sabatons";
      case STONE:
        return "Greaves";
      case WOOD:
      default:
        return "Shoes";
    }
  }

}
