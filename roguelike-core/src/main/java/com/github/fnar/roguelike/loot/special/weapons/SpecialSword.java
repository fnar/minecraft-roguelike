package com.github.fnar.roguelike.loot.special.weapons;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialSword extends SpecialWeapon {

  public SpecialSword(Random random, int level) {
    this(random, Quality.rollWeaponQuality(random, level));
  }

  public SpecialSword(Random random, Quality quality) {
    withQuality(quality);
    withItem(getSwordItem());
    withName(quality.getDescriptor() + " Blade");
    withSwordEnchantments(random);
    withCommonEnchantments(random);
  }

  private Item getSwordItem() {
    switch (quality) {
      default:
      case WOOD:
        return Items.WOODEN_SWORD;
      case STONE:
        return Items.STONE_SWORD;
      case IRON:
        return Items.IRON_SWORD;
      case GOLD:
        return Items.GOLDEN_SWORD;
      case DIAMOND:
        return Items.DIAMOND_SWORD;
    }
  }

  public void withSwordEnchantments(Random random) {
    withSharpness(random);
    withLooting(random);
    withFiery(random);
  }

  public void withSharpness(Random random) {
    if (random.nextBoolean()) {
      return;
    }
    int enchantmentLevel = random.nextInt(6);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), enchantmentLevel);
  }

  public void withLooting(Random random) {
    if (random.nextBoolean()) {
      return;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.LOOTING), enchantmentLevel);
    if (enchantmentLevel < 3) {
      withPrefix("Burglar's");
    } else {
      withLore("Once belonged to a king of hidden desert thieves.", TextFormat.DARKGREEN);
      withPrefix("Bandit King's");
    }
  }

  public void withFiery(Random random) {
    if (random.nextBoolean()) {
      return;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), enchantmentLevel);
    if (enchantmentLevel == 1) {
      withLore("Warm to the touch", TextFormat.YELLOW);
      withPrefix("Ember");
      return;
    }

    if (enchantmentLevel == 2) {
      withLore("Almost too hot to hold", TextFormat.RED);
      withPrefix("Fiery");
      return;
    }
    withLore("From the fiery depths", TextFormat.DARKRED);
    withSuffix("of the Inferno");
  }

}
