package com.github.fnar.roguelike.loot.special.tools;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialTool extends SpecialEquipment {

  public static RldItemStack createTool(Random random, Quality quality) {
    return chooseTool(random, quality).complete();
  }

  private static SpecialTool chooseTool(Random random, Quality quality) {
    switch (random.nextInt(4)) {
      case 0:
        return new SpecialPickaxe(random, quality);
      case 1:
        return new SpecialAxe(random, quality);
      case 2:
        return new SpecialShovel(random, quality);
      default:
      case 3:
        return new SpecialHoe(random, quality);
    }
  }

  protected void withFortune(Random random) {
    if (random.nextDouble() >= .15) {
      return;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchantment.Effect.FORTUNE, enchantmentLevel);
    if (random.nextBoolean()) {
      withSuffix("of Prospecting");
    } else {
      withPrefix("Dwarven");
    }
  }

  protected void withSilkTouch(Random random) {
    if (random.nextDouble() >= .15) {
      return;
    }
    withEnchantment(Enchantment.Effect.SILK_TOUCH, 1);
    if (random.nextBoolean()) {
      withPrefix("Precision");
    } else {
      withPrefix("Elven");
    }
  }

  protected void withEfficiency(Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchantment.Effect.EFFICIENCY, enchantmentLevel);
    if (enchantmentLevel >= 3) {
      withPrefix("Artisan's");
    }
  }

  protected SpecialTool withToolEnchantments(Random random) {
    withEfficiency(random);
    withSilkTouch(random);
    withFortune(random);
    return this;
  }

}
