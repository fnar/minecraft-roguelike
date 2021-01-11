package com.github.srwaggon.roguelike.worldgen;

import com.github.srwaggon.roguelike.minecraft.item.BlockItem;
import com.github.srwaggon.roguelike.minecraft.item.Potion;
import com.github.srwaggon.roguelike.minecraft.item.PotionItemStackMapper;
import com.github.srwaggon.roguelike.minecraft.item.RldItem;
import com.github.srwaggon.roguelike.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;

public class ItemStackMapper {

  public static ItemStack map(RldItemStack rldItemStack) {

    RldItem item = rldItemStack.getItem();

    switch (item.getItemType()) {

      case BLOCK:
        return new ItemStack(BlockItemMapper.mapBlock((BlockItem) item));
      case POTION:
        return PotionItemStackMapper.map((Potion) item);
    }

    return null;
  }

}

