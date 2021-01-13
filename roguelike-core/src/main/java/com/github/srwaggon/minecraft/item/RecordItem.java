package com.github.srwaggon.minecraft.item;

import greymerk.roguelike.treasure.loot.RecordSong;

public class RecordItem implements RldItem {

  private RecordSong song;

  public static RecordItem newRecord() {
    return new RecordItem();
  }

  @Override
  public ItemType getItemType() {
    return ItemType.RECORD;
  }

  public RecordItem withSong(RecordSong song) {
    this.song = song;
    return this;
  }

  public RecordSong getSong() {
    return song;
  }
}
