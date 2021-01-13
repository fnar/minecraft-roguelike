package greymerk.roguelike.treasure.loot.provider;

import com.github.srwaggon.minecraft.item.potion.Potion;
import com.github.srwaggon.minecraft.item.potion.PotionMapper1_12;

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
    PotionType type = PotionType.chooseRandomAmong(rand, potionTypes);
    return PotionMapper1_12.map(Potion.newPotion().withForm(PotionForm.REGULAR).withType(type).withAmplification().asItemStack());
  }

}
