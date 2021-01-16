package com.github.srwaggon.minecraft.item;

import java.util.Random;

public class Record implements RldItem {

  private Song song;

  public static Record newRecord() {
    return new Record();
  }

  @Override
  public ItemType getItemType() {
    return ItemType.RECORD;
  }

  public Record withSong(Song song) {
    this.song = song;
    return this;
  }

  public Song getSong() {
    return song;
  }

  public enum Song {

    THIRTEEN,
    CAT,
    BLOCKS,
    CHIRP,
    FAR,
    MALL,
    MELLOHI,
    STAL,
    STRAD,
    WARD,
    ELEVEN,
    WAIT;

    public static Song chooseRandom(Random random) {
      return Song.values()[random.nextInt(Song.values().length)];
    }
  }
}
