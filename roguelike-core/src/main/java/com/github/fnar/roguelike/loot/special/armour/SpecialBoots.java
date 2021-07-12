package com.github.fnar.roguelike.loot.special.armour;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.Quality.*;

public class SpecialBoots extends SpecialArmour {

  public SpecialBoots(Random random, int level) {
    this(random, rollRandomQualityByLevel(random, level, Equipment.FEET));
  }

  public SpecialBoots(Random random, Quality quality) {
    withQuality(quality);
    withItem(getSpecialBootsItem());
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
    withEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), quality == DIAMOND ? 4 : 1 + random.nextInt(3));
    withEnchantment(Enchant.getEnchant(Enchant.PROTECTION), Enchant.getProtectionLevel(quality, random));
    withSuffix("of Lightness");
  }

  private Item getSpecialBootsItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_BOOTS;
      case GOLD:
        return Items.GOLDEN_BOOTS;
      case IRON:
        return Items.IRON_BOOTS;
      case STONE:
        return Items.CHAINMAIL_BOOTS;
      case WOOD:
      default:
        return Items.LEATHER_BOOTS;
    }
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
