package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Miscellaneous;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

public class ItemEnchBonus extends LootItem {

  public ItemEnchBonus(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random random, int level) {
    return random.nextBoolean()
        ? Miscellaneous.Type.EXPERIENCE_BOTTLE.asItem().asStack().withCount(1 + random.nextInt(5))
        : Miscellaneous.Type.ENDER_PEARL.asItem().asStack().withCount(1 + random.nextInt(2));
  }


}
