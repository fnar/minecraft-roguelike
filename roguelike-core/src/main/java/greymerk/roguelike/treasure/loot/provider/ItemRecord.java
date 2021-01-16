package greymerk.roguelike.treasure.loot.provider;

import com.github.srwaggon.minecraft.item.ItemMapper1_12;
import com.github.srwaggon.minecraft.item.Record;

import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemRecord extends ItemBase {

  public ItemRecord(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    Record.Song song = Record.Song.chooseRandom(rand);
    return ItemMapper1_12.map(Record.newRecord().withSong(song).asItemStack());
  }
}
