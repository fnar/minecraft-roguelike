package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Quality;

public class ItemSmithy extends ItemBase {

  public ItemSmithy(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random random, int level) {
    return new SpecialSword(random, Quality.IRON).complete();
  }
}
