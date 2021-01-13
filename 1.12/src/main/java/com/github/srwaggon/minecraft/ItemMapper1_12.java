package com.github.srwaggon.minecraft;

import com.github.srwaggon.minecraft.item.BlockItem;
import com.github.srwaggon.minecraft.item.RecordMapper1_12;
import com.github.srwaggon.minecraft.item.RldItem;
import com.github.srwaggon.minecraft.item.RldItemStack;
import com.github.srwaggon.minecraft.item.potion.PotionMapper1_12;

import net.minecraft.item.ItemStack;

public class ItemMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {

    RldItem item = rldItemStack.getItem();

    switch (item.getItemType()) {

      case BLOCK:
        return new ItemStack(BlockItemMapper.mapBlock((BlockItem) item));
      case POTION:
        return PotionMapper1_12.map(rldItemStack);
      case RECORD:
        return RecordMapper1_12.map(rldItemStack);
    }

    return null;
  }

}

