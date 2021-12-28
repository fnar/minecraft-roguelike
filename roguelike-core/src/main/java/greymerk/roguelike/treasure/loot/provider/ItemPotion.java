package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.ItemMapper1_12;
import com.github.fnar.minecraft.item.Potion;

import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemPotion extends ItemBase {

  public ItemPotion(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    final Potion.Effect[] effects = new Potion.Effect[]{
        Potion.Effect.HEALING,
        Potion.Effect.STRENGTH,
        Potion.Effect.SWIFTNESS,
        Potion.Effect.REGENERATION
    };
    Potion.Effect effect = Potion.Effect.chooseRandomAmong(rand, effects);
    return new ItemMapper1_12().map(Potion.newPotion().withForm(Potion.Form.REGULAR).withEffect(effect).withAmplification().asItemStack());
  }

}
