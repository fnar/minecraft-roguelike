package com.github.fnar.roguelike.loot.special.tools;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.util.TextFormat;

public class SpecialHoe extends SpecialTool {

  public SpecialHoe(Random random, int level) {
    this(random, ItemTool.rollToolQuality(random, level));
  }

  public SpecialHoe(Random random, Quality quality) {
    withName(quality == Quality.DIAMOND ? "Useless Hoe" : quality.getDescriptor() + " Hoe");
    withItem(getHoeItem(quality));
    withToolEnchantments(random);
    withCommonEnchantments(random);
    if (quality == Quality.DIAMOND) {
      withLore("Don't craft a diamond hoe", TextFormat.GRAY);
    }
  }

  private Item getHoeItem(Quality quality) {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_HOE;
      case GOLD:
        return Items.GOLDEN_HOE;
      case IRON:
        return Items.IRON_HOE;
      case STONE:
        return Items.STONE_HOE;
      case WOOD:
      default:
        return Items.WOODEN_HOE;
    }
  }
}
