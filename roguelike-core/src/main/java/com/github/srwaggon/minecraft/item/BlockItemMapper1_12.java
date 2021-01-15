package com.github.srwaggon.minecraft.item;

import com.github.srwaggon.minecraft.block.BlockMapper1_12;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.MetaBlock1_2;

public class BlockItemMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {
    return new ItemStack(map((BlockItem)rldItemStack.getItem()));
  }

  private static Item map(BlockItem item) {
    MetaBlock1_2 metaBlock = BlockMapper1_12.map(item.getBlockType());
    return Item.getItemFromBlock(metaBlock.getBlock());
  }
}
