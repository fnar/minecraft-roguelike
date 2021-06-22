package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecordMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {
    return map((Record) rldItemStack.getItem());
  }

  private static ItemStack map(Record item) {
    return new ItemStack(getId(item.getSong()));
  }

  private static Item getId(Record.Song type) {

    switch (type) {
      case THIRTEEN:
        return Items.RECORD_13;
      default:
      case CAT:
        return Items.RECORD_CAT;
      case BLOCKS:
        return Items.RECORD_BLOCKS;
      case CHIRP:
        return Items.RECORD_CHIRP;
      case FAR:
        return Items.RECORD_FAR;
      case MALL:
        return Items.RECORD_MALL;
      case MELLOHI:
        return Items.RECORD_MELLOHI;
      case STAL:
        return Items.RECORD_STAL;
      case STRAD:
        return Items.RECORD_STRAD;
      case WARD:
        return Items.RECORD_WARD;
      case ELEVEN:
        return Items.RECORD_11;
      case WAIT:
        return Items.RECORD_WAIT;
    }
  }
}
