package com.github.fnar.roguelike.loot.special.tools;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemTool;

public class SpecialPickaxe extends SpecialTool {

  public SpecialPickaxe(Random random, int level) {
    this(random, ItemTool.rollToolQuality(random, level));
  }

  public SpecialPickaxe(Random random, Quality quality) {
    withQuality(quality);
    withName(quality.getDescriptor() + " Pick");
    withItem(getPickaxeItem());
    withToolEnchantments(random);
    withCommonEnchantments(random);
  }

  private Item getPickaxeItem() {
    switch (quality) {
      case DIAMOND:
        return Items.DIAMOND_PICKAXE;
      case GOLD:
        return Items.GOLDEN_PICKAXE;
      case IRON:
        return Items.IRON_PICKAXE;
      case STONE:
        return Items.STONE_PICKAXE;
      case WOOD:
      default:
        return Items.WOODEN_PICKAXE;
    }
  }

}
