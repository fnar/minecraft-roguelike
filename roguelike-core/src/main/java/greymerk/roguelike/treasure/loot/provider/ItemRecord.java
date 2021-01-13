package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.RecordSong;

public class ItemRecord extends ItemBase {

  public ItemRecord(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    return RecordSong.getRandomRecord(rand);
  }


}
