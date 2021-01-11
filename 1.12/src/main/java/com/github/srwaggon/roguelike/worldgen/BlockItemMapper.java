package com.github.srwaggon.roguelike.worldgen;

import com.github.srwaggon.roguelike.minecraft.item.BlockItem;
import com.github.srwaggon.roguelike.worldgen.block.BlockMapper1_12;

import net.minecraft.item.Item;

import greymerk.roguelike.worldgen.MetaBlock1_2;

public class BlockItemMapper {

  public static Item mapBlock(BlockItem item) {
    MetaBlock1_2 metaBlock = BlockMapper1_12.map(item.getBlockType());
    return Item.getItemFromBlock(metaBlock.getBlock());
  }
}
