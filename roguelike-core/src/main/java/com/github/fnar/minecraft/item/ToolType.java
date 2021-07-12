package com.github.fnar.minecraft.item;

import net.minecraft.item.Item;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

import static net.minecraft.init.Items.DIAMOND_AXE;
import static net.minecraft.init.Items.DIAMOND_HOE;
import static net.minecraft.init.Items.DIAMOND_PICKAXE;
import static net.minecraft.init.Items.DIAMOND_SHOVEL;
import static net.minecraft.init.Items.GOLDEN_AXE;
import static net.minecraft.init.Items.GOLDEN_HOE;
import static net.minecraft.init.Items.GOLDEN_PICKAXE;
import static net.minecraft.init.Items.GOLDEN_SHOVEL;
import static net.minecraft.init.Items.IRON_AXE;
import static net.minecraft.init.Items.IRON_HOE;
import static net.minecraft.init.Items.IRON_PICKAXE;
import static net.minecraft.init.Items.IRON_SHOVEL;
import static net.minecraft.init.Items.STONE_AXE;
import static net.minecraft.init.Items.STONE_HOE;
import static net.minecraft.init.Items.STONE_PICKAXE;
import static net.minecraft.init.Items.STONE_SHOVEL;
import static net.minecraft.init.Items.WOODEN_AXE;
import static net.minecraft.init.Items.WOODEN_HOE;
import static net.minecraft.init.Items.WOODEN_PICKAXE;
import static net.minecraft.init.Items.WOODEN_SHOVEL;

public enum ToolType {
  AXE,
  HOE,
  PICKAXE,
  SHOVEL
  ;

  public static ToolType random(Random random) {
    int choice = random.nextInt(values().length);
    return values()[choice];
  }

  public Item asItem(Quality quality) {
    switch(this) {
      case AXE:
        return EquipmentType.asItem(quality, WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE);
      case HOE:
        return EquipmentType.asItem(quality, WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE);
      case PICKAXE:
        return EquipmentType.asItem(quality, WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE);
      case SHOVEL:
        return EquipmentType.asItem(quality, WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL);
    }
    throw new IllegalArgumentException("Unexpected ToolType: " + this);
  }
}
