package com.github.fnar.roguelike.loot.special.tools;

import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialTool extends SpecialEquipment {

  public static RldItemStack createTool(Random random, Quality quality) {
    return chooseTool(random, quality).asStack();
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
    withEnchantment(Enchantment.Effect.FORTUNE.atLevel(enchantmentLevel));
    if (random.nextBoolean()) {
      //withSuffix("of Prospecting");
      withSuffix("fortune_0");
    } else {
      //withPrefix("Dwarven");
      withPrefix("fortune_1");
    }
  }

  protected void withSilkTouch(Random random) {
    if (random.nextDouble() >= .15) {
      return;
    }
    withEnchantment(Enchantment.Effect.SILK_TOUCH);
    if (random.nextBoolean()) {
      //withPrefix("Precision");
      withPrefix("silk_0");
    } else {
      //withPrefix("Elven");
      withPrefix("silk_1");
    }
  }

  protected void withEfficiency(Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchantment.Effect.EFFICIENCY.atLevel(enchantmentLevel));
    if (enchantmentLevel >= 3) {
      //withPrefix("Artisan's");
      withPrefix("eff3");
    }
  }

  protected SpecialTool withToolEnchantments(Random random) {
    withEfficiency(random);
    withSilkTouch(random);
    withFortune(random);
    return this;
  }

}
