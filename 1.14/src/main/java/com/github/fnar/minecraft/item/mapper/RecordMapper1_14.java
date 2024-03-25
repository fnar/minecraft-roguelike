package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.Record;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class RecordMapper1_14 extends BaseItemMapper1_14<Record> {

  @Override
  public Class<Record> getClazz() {
    return Record.class;
  }

  public ItemStack map(Record item) throws CouldNotMapItemException {
    return new ItemStack(getId(item));
  }

  private Item getId(Record item) throws CouldNotMapItemException {

    switch (item.getSong()) {
      case THIRTEEN:
        return Items.MUSIC_DISC_13;
      case CAT:
        return Items.MUSIC_DISC_CAT;
      case BLOCKS:
        return Items.MUSIC_DISC_BLOCKS;
      case CHIRP:
        return Items.MUSIC_DISC_CHIRP;
      case FAR:
        return Items.MUSIC_DISC_FAR;
      case MALL:
        return Items.MUSIC_DISC_MALL;
      case MELLOHI:
        return Items.MUSIC_DISC_MELLOHI;
      case STAL:
        return Items.MUSIC_DISC_STAL;
      case STRAD:
        return Items.MUSIC_DISC_STRAD;
      case WARD:
        return Items.MUSIC_DISC_WARD;
      case ELEVEN:
        return Items.MUSIC_DISC_11;
      case WAIT:
        return Items.MUSIC_DISC_WAIT;
    }
    throw new CouldNotMapItemException(item);
  }
}
