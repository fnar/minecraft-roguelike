package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

public class PotionLootItem extends LootItem {

  public PotionLootItem(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    final Potion.Effect[] effects = new Potion.Effect[]{
        Potion.Effect.HEALING,
        Potion.Effect.STRENGTH,
        Potion.Effect.SWIFTNESS,
        Potion.Effect.REGENERATION
    };

    Potion.Effect effect = Potion.Effect.chooseRandomAmong(random, effects);

    return Potion.newPotion().withForm(Potion.Form.REGULAR).withEffect(effect).withAmplification().asStack();
  }

}
