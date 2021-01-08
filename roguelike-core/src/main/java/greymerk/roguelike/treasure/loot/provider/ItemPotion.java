package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionType;
import greymerk.roguelike.treasure.loot.PotionForm;

public class ItemPotion extends ItemBase {

  public ItemPotion(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    final PotionType[] potionTypes = new PotionType[]{
        PotionType.HEALING,
        PotionType.STRENGTH,
        PotionType.SWIFTNESS,
        PotionType.REGEN
    };
    PotionType type = potionTypes[rand.nextInt(potionTypes.length)];
    return PotionType.getSpecific(PotionForm.REGULAR, type, true, false);
  }
}
