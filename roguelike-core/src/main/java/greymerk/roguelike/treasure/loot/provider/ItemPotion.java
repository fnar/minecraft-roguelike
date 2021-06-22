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
    final Potion.Type[] types = new Potion.Type[]{
        Potion.Type.HEALING,
        Potion.Type.STRENGTH,
        Potion.Type.SWIFTNESS,
        Potion.Type.REGENERATION
    };
    Potion.Type type = Potion.Type.chooseRandomAmong(rand, types);
    return ItemMapper1_12.map(Potion.newPotion().withForm(Potion.Form.REGULAR).withType(type).withAmplification().asItemStack());
  }

}
