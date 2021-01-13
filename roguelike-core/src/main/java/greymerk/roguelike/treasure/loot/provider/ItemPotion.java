package greymerk.roguelike.treasure.loot.provider;

import com.github.srwaggon.minecraft.item.ItemMapper1_12;
import com.github.srwaggon.minecraft.item.Potion;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.PotionType;

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
        PotionType.REGENERATION
    };
    PotionType type = PotionType.chooseRandomAmong(rand, potionTypes);
    return ItemMapper1_12.map(Potion.newPotion().withForm(PotionForm.REGULAR).withType(type).withAmplification().asItemStack());
  }

}
