package com.github.fnar.roguelike.loot.special.weapons;

import com.github.fnar.minecraft.item.WeaponType;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialBow extends SpecialWeapon {

  public SpecialBow(Random random, int level) {
    this(random, Quality.rollWeaponQuality(random, level));
  }

  public SpecialBow(Random random, Quality quality) {

    withQuality(quality);
    withItem(WeaponType.getBowItem());
    withPower(random);

    switch (quality) {
      case WOOD:
      case STONE:
        withName("Yew Longbow");
        withLore("Superior craftsmanship", TextFormat.DARKGREEN);
        break;
      default:
      case IRON:
        withName("Laminated Bow");
        withLore("Highly polished", TextFormat.DARKGREEN);
      case GOLD:
        if (random.nextBoolean()) {
          withEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
          withName("Elven Bow");
          withLore("Beautifully crafted", TextFormat.DARKGREEN);
        }

        if (random.nextBoolean()) {
          withEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
          withName("Faerie Bow");
          withLore("Blessed by the fae", TextFormat.DARKGREEN);
        }
        withName("Recurve Bow");
        withLore("Curves outward toward the target", TextFormat.DARKGREEN);
        break;
      case DIAMOND:
        withEnchantment(Enchant.getEnchant(Enchant.FLAME), 1);
        withName("Eldritch Bow");
        withLore("Warm to the touch", TextFormat.DARKGREEN);
        break;
    }
    withUnbreaking(random);
  }

  public void withPower(Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.POWER), enchantmentLevel);
  }
}
