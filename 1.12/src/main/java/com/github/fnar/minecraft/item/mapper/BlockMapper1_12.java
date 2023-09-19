package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.item.BlockItem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockMapper1_12 extends BaseItemMapper1_12<BlockItem> {

  @Override
  public Class<BlockItem> getClazz() {
    return BlockItem.class;
  }

  @Override
  public ItemStack map(BlockItem blockItem) {
    SingleBlockBrush brush = blockItem.getBlockType().getBrush();
    Block block = com.github.fnar.minecraft.block.BlockMapper1_12.map(brush).getBlock();
    Item item = Item.getItemFromBlock(block);
    return new ItemStack(item);
  }
}
