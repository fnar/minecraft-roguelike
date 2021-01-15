package greymerk.roguelike.treasure.loot;

import java.util.Random;

public enum RecordSong {

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

  public static RecordSong chooseRandom(Random random) {
    return RecordSong.values()[random.nextInt(RecordSong.values().length)];
  }

}
