package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.util.IWeighted;

public abstract class LootItem implements IWeighted<RldItemStack> {

  int level;
  private final int weight;

  public LootItem(int weight) {
    this.weight = weight;
    this.level = 0;
  }

  public LootItem(int weight, int level) {
    this.weight = weight;
    this.level = level;
  }

  public abstract RldItemStack getLootItem(Random rand, int level);

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public RldItemStack get(Random rand) {
    return getLootItem(rand, level);
  }

  public static int getEnchantmentLevel(Random rand, int level) {

    switch (level) {
      case 4:
        return 30 + rand.nextInt(10);
      case 3:
        return 15 + rand.nextInt(15);
      case 2:
        return 5 + rand.nextInt(15);
      case 1:
        return 1 + rand.nextInt(10);
      case 0:
        return 1 + rand.nextInt(5);
      default:
        return 1;
    }
  }
}
