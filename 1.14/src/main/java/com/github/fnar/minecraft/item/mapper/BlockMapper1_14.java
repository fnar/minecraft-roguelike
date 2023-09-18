package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.BlockItem;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.NotImplementedException;

public class BlockMapper1_14 extends BaseItemMapper1_14<BlockItem> {

  @Override
  public Class<BlockItem> getClazz() {
    return BlockItem.class;
  }

  // TODO: Implement
  @Override
  public ItemStack map(BlockItem block) {
//    MetaBlock1_12 metaBlock = com.github.fnar.minecraft.block.BlockMapper1_14.map(block.getBlockType());
//    Item item = Item.getItemFromBlock(metaBlock.getBlock());
//    return new ItemStack(item);
    throw new NotImplementedException("1.14 not yet supported");
  }
}
