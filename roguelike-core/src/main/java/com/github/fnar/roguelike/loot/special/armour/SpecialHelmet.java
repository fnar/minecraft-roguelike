package com.github.fnar.roguelike.loot.special.armour;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;

import static greymerk.roguelike.treasure.loot.provider.ItemArmour.rollArmourQuality;

public class SpecialHelmet extends SpecialArmour {

  public SpecialHelmet(Random random, int level) {
    this(random, rollArmourQuality(random, level));
  }

  public SpecialHelmet(Random random, Quality quality) {
    withQuality(quality);
    withItem(getSpecialHelmetItem());
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
    withEnchantment(Enchant.getEnchant(Enchant.RESPIRATION), 3);
    withEnchantment(Enchant.getEnchant(Enchant.AQUAAFFINITY), 1);
    withEnchantment(Enchant.getEnchant(Enchant.PROTECTION), Enchant.getProtectionLevel(quality, random));
    withSuffix("of Diving");
  }

  private Item getSpecialHelmetItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_HELMET;
      case GOLD:
        return Items.GOLDEN_HELMET;
      case IRON:
        return Items.IRON_HELMET;
      case STONE:
        return Items.CHAINMAIL_HELMET;
      case WOOD:
      default:
        return Items.LEATHER_HELMET;
    }
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
