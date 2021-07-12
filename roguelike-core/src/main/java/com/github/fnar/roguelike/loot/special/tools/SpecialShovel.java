package com.github.fnar.roguelike.loot.special.tools;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class SpecialShovel extends SpecialTool {

  public SpecialShovel(Random random, int level) {
    this(random, ItemTool.rollToolQuality(random, level));
  }

  public SpecialShovel(Random random, Quality quality) {
    withName(quality == Quality.IRON ? "Grave Spade" : quality.getDescriptor() + " Spade");
    withItem(getShovelItem(quality));
    withToolEnchantments(random);
    withCommonEnchantments(random);
  }

  private Item getShovelItem(Quality quality) {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_SHOVEL;
      case GOLD:
        return Items.GOLDEN_SHOVEL;
      case IRON:
        return Items.IRON_SHOVEL;
      case STONE:
        return Items.STONE_SHOVEL;
      case WOOD:
      default:
        return Items.WOODEN_SHOVEL;
    }
  }

}
