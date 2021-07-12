package com.github.fnar.roguelike.loot.special.tools;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class SpecialAxe extends SpecialTool {

  public SpecialAxe(Random random, int level) {
    this(random, Quality.rollToolQuality(random, level));
  }

  public SpecialAxe(Random random, Quality quality) {
    withQuality(quality);
    if (quality == Quality.IRON) {
      withName("Woodland Hatchet");
    } else {
      withName(quality.getDescriptor() + " Axe");
    }
    withItem(getAxeItem());
    withToolEnchantments(random);
    withCommonEnchantments(random);
  }

  private Item getAxeItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_AXE;
      case GOLD:
        return Items.GOLDEN_AXE;
      case IRON:
        return Items.IRON_AXE;
      case STONE:
        return Items.STONE_AXE;
      case WOOD:
      default:
        return Items.WOODEN_AXE;
    }
  }

}
