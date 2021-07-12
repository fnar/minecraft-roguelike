package com.github.fnar.roguelike.loot.special.tools;

import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class SpecialTool extends SpecialEquipment {

  public static ItemStack createTool(Random random, int level) {
    Quality quality = ItemTool.rollToolQuality(random, level);
    return createTool(random, quality);
  }

  public static ItemStack createTool(Random random, Quality quality) {
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
    if (random.nextInt(10) != 0) {
      return;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.FORTUNE), enchantmentLevel);
    withSuffix("of Prospecting");
  }

  protected void withSilkTouch(Random random) {
    if (random.nextInt(10) != 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
    withPrefix("Precision");
  }

  protected void withEfficiency(Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    withEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), enchantmentLevel);
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
