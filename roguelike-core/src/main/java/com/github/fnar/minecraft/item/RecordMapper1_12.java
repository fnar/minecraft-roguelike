package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecordMapper1_12 extends BaseItemMapper1_12<Record> implements RecordMapper {

  @Override
  public Class<Record> getClazz() {
    return Record.class;
  }

  public ItemStack map(Record item) {
    return new ItemStack(getId(item));
  }

  private Item getId(Record item) {

    switch (item.getSong()) {
      case THIRTEEN:
        return Items.RECORD_13;
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
    throw new CouldNotMapItemException(item);
  }
}
