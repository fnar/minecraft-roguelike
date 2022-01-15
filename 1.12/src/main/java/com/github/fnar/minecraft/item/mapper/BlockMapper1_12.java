package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.BlockItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.MetaBlock1_12;

public class BlockMapper1_12 extends BaseItemMapper1_12<BlockItem> {

  @Override
  public Class<BlockItem> getClazz() {
    return BlockItem.class;
  }

  @Override
  public ItemStack map(BlockItem block) {
    MetaBlock1_12 metaBlock = com.github.fnar.minecraft.block.BlockMapper1_12.map(block.getBlockType());
    Item item = Item.getItemFromBlock(metaBlock.getBlock());
    return new ItemStack(item);
  }
}
