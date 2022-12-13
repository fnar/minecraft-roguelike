package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class ItemSmithy extends LootItem {

  public ItemSmithy(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return new SpecialSword(random, Quality.IRON).complete();
  }
}
