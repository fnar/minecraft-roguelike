package com.github.fnar.roguelike.loot.special.tools;

import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.ToolType;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class SpecialShovel extends SpecialTool {

  public SpecialShovel(Random random, int level) {
    this(random, Equipment.rollQuality(random, level));
  }

  public SpecialShovel(Random random, Quality quality) {
    withName(quality == Quality.IRON ? "Grave Spade" : quality.getDescriptor() + " Spade");
    withRldItem(getItem(quality));
    withToolEnchantments(random);
    withCommonEnchantments(random);
  }

  private RldItem getItem(Quality quality) {
    return ToolType.SHOVEL.asItem().withQuality(quality);
  }

}
