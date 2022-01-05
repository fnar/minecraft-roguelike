package com.github.fnar.roguelike.loot.special.weapons;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialBow extends SpecialEquipment {

  public SpecialBow(Random random, int level) {
    this(random, Equipment.rollQuality(random, level));
  }

  public SpecialBow(Random random, Quality quality) {

    withQuality(quality);
    withRldItem(WeaponType.BOW.asItem());
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
          withEnchantment(Enchantment.Effect.INFINITY, 1);
          withName("Elven Bow");
          withLore("Beautifully crafted", TextFormat.DARKGREEN);
        }

        if (random.nextBoolean()) {
          withEnchantment(Enchantment.Effect.MENDING, 1);
          withName("Faerie Bow");
          withLore("Blessed by the fae", TextFormat.DARKGREEN);
        }
        withName("Recurve Bow");
        withLore("Curves outward toward the target", TextFormat.DARKGREEN);
        break;
      case DIAMOND:
        withEnchantment(Enchantment.Effect.FLAME, 1);
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
    withEnchantment(Enchantment.Effect.POWER, enchantmentLevel);
  }
}
