package com.github.fnar.roguelike.loot.special.armour;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.Quality.DIAMOND;
import static greymerk.roguelike.treasure.loot.provider.ToolQualityOddsTable.rollToolQuality;

public class SpecialBoots extends SpecialArmour {

  public SpecialBoots(Random random, int level) {
    this(random, rollToolQuality(random, level));
  }

  public SpecialBoots(Random random, Quality quality) {
    withQuality(quality);
    withRldItem(getItem());
    withName(getSpecialBootsName());
    withBootsEnchantments(random);
    withCommonEnchantments(random);
    withArmourPrefix();
    withRandomColourIfWood();
  }

  private void withBootsEnchantments(Random random) {
    if (random.nextInt(20) == 0) {
      withFeatherFalling(random);
    } else {
      withArmorEnchantments(random);
    }
  }

  private void withFeatherFalling(Random random) {
    withEnchantment(Enchantment.Effect.FEATHER_FALLING, quality == DIAMOND ? 4 : 1 + random.nextInt(3));
    withEnchantment(Enchantment.Effect.PROTECTION, SpecialEquipment.getProtectionLevel(quality, random));
    withSuffix("of Lightness");
  }

  private RldItem getItem() {
    return ArmourType.BOOTS.asItem().withQuality(quality);
  }

  private String getSpecialBootsName() {
    switch (quality) {
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
