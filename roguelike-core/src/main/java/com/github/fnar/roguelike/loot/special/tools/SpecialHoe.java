package com.github.fnar.roguelike.loot.special.tools;

import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.ToolType;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class SpecialHoe extends SpecialTool {

  public SpecialHoe(Random random, Quality quality) {
    withName(quality == Quality.DIAMOND ? "Useless Hoe" : quality.getDescriptor() + " Hoe");
    withRldItem(getItem(quality));
    withToolEnchantments(random);
    withCommonEnchantments(random);
    if (quality == Quality.DIAMOND) {
      withLore("Don't craft a diamond hoe", TextFormat.GRAY);
    }
  }

  private RldItem getItem(Quality quality) {
    return ToolType.HOE.asItem().withQuality(quality);
  }
}
