package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Record;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

public class RecordLootItem extends LootItem {

  public RecordLootItem(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    Record.Song song = Record.Song.chooseRandom(random);
    return Record.newRecord().withSong(song).asStack();
  }
}
