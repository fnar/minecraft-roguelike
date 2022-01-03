package com.github.fnar.minecraft.item;

import net.minecraft.item.Item;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
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
  FISHING_ROD,
  FLINT_AND_STEEL,
  HOE,
  PICKAXE,
  SHEARS,
  SHIELD,
  SHOVEL,
  ;

  public static ToolType randomAmong(Random random, List<ToolType> values) {
    return values.get(random.nextInt(values.size()));
  }

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

  public Tool asItem() {
    return new Tool(this);
  }

  public Equipment asEquipment() {
    switch(this) {
      case AXE:
        return Equipment.AXE;
      case HOE:
        return Equipment.HOE;
      case PICKAXE:
        return Equipment.PICK;
      case SHOVEL:
        return Equipment.SHOVEL;
    }
    throw new IllegalArgumentException("Unexpected ToolType: " + this);
  }
}
