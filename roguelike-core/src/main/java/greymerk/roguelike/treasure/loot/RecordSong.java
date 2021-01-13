package greymerk.roguelike.treasure.loot;

import com.github.srwaggon.minecraft.item.RecordItem;
import com.github.srwaggon.minecraft.item.RecordMapper1_12;

import net.minecraft.item.ItemStack;

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

  public static ItemStack getRandomRecord(Random rand) {
    RecordSong song = RecordSong.values()[rand.nextInt(RecordSong.values().length)];
    return RecordMapper1_12.map(RecordItem.newRecord().withSong(song));
  }

}
