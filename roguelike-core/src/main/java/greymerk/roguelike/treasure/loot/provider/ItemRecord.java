package greymerk.roguelike.treasure.loot.provider;

import com.github.srwaggon.minecraft.item.ItemMapper1_12;
import com.github.srwaggon.minecraft.item.RecordItem;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.RecordSong;

public class ItemRecord extends ItemBase {

  public ItemRecord(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    RecordSong song = RecordSong.chooseRandom(rand);
    return ItemMapper1_12.map(RecordItem.newRecord().withSong(song).asItemStack());
  }


}
