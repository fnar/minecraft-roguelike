package com.github.fnar.minecraft.item;

import net.minecraft.item.Item;

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

public class ToolMapper1_12 {
  public Item map(Quality quality, ToolType toolType) {
    switch(toolType) {
      case AXE:
        return EquipmentMapper1_12.map(quality, WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE);
      case HOE:
        return EquipmentMapper1_12.map(quality, WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE);
      case PICKAXE:
        return EquipmentMapper1_12.map(quality, WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE);
      case SHOVEL:
        return EquipmentMapper1_12.map(quality, WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL);
    }
    return null;
  }

}
