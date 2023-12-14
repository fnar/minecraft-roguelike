package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Tool;
import com.github.fnar.minecraft.item.ToolType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import greymerk.roguelike.treasure.loot.Quality;

public class ToolMapper1_14 extends RldBaseItemMapper1_14<Tool> {

  @Override
  public Class<Tool> getClazz() {
    return Tool.class;
  }

  @Override
  public ItemStack map(Tool rldItem) throws CouldNotMapItemException {
    Item item = map(rldItem, rldItem.getToolType(), rldItem.getQuality());
    return map(rldItem, item);
  }

  private Item map(Tool tool, ToolType toolType, Quality quality) throws CouldNotMapItemException {
    switch (toolType) {
      case AXE:
        return EquipmentMapper1_14.map(quality, Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE);
      case FISHING_ROD:
        return Items.FISHING_ROD;
      case FLINT_AND_STEEL:
        return Items.FLINT_AND_STEEL;
      case HOE:
        return EquipmentMapper1_14.map(quality, Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE);
      case PICKAXE:
        return EquipmentMapper1_14.map(quality, Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE);
      case SHEARS:
        return Items.SHEARS;
      case SHOVEL:
        return EquipmentMapper1_14.map(quality, Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL);
    }
    throw new CouldNotMapItemException(tool);
  }

}
