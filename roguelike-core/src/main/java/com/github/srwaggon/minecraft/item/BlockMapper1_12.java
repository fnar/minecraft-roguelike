package com.github.srwaggon.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.MetaBlock1_2;

public class BlockMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {
    return new ItemStack(map((Block)rldItemStack.getItem()));
  }

  private static Item map(Block item) {
    MetaBlock1_2 metaBlock = com.github.srwaggon.minecraft.block.BlockMapper1_12.map(item.getBlockType());
    return Item.getItemFromBlock(metaBlock.getBlock());
  }
}
