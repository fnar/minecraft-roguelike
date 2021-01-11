package com.github.srwaggon.minecraft;

import com.github.srwaggon.minecraft.item.BlockItem;
import com.github.srwaggon.minecraft.item.RldItem;
import com.github.srwaggon.minecraft.item.RldItemStack;
import com.github.srwaggon.minecraft.item.potion.PotionItemStackMapper;

import net.minecraft.item.ItemStack;

public class ItemStackMapper {

  public static ItemStack map(RldItemStack rldItemStack) {

    RldItem item = rldItemStack.getItem();

    switch (item.getItemType()) {

      case BLOCK:
        return new ItemStack(BlockItemMapper.mapBlock((BlockItem) item));
      case POTION:
        return PotionItemStackMapper.map(rldItemStack);
    }

    return null;
  }

}

