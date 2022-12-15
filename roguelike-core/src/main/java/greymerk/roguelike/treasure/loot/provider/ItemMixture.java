package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionMixture;

public class ItemMixture extends LootItem {

  private final PotionMixture potionMixture;

  public ItemMixture(int weight, PotionMixture potionMixture) {
    super(weight, 0);
    this.potionMixture = potionMixture;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return PotionMixture.getPotion(random, potionMixture);
  }
}
