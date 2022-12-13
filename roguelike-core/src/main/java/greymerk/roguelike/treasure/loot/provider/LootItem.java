package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.Difficulty;
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

  public static boolean isSpecial(Random random) {
    return random.nextDouble() < .05;
  }

  public static boolean isEnchanted(Difficulty difficulty, Random random, int level) {
    switch (difficulty) {
      default:
      case PEACEFUL:
        return false;
      case EASY:
        return random.nextDouble() < .05 + .01 * level;
      case NORMAL:
        return random.nextDouble() < .05 + .03 * level;
      case DIFFICULT:
        return random.nextDouble() < .10 + .05 * level;
    }
  }

  public abstract RldItemStack getLootItem(Random random);

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public RldItemStack get(Random rand) {
    return getLootItem(rand);
  }

  public static int getEnchantmentLevel(Random random, int level) {
    switch (level) {
      case 0:
        return 1 + random.nextInt(4);
      case 1:
        return 5 + random.nextInt(5);
      case 2:
        return 10 + random.nextInt(10);
      case 3:
        return 15 + random.nextInt(15);
      case 4:
        return 20 + random.nextInt(20);
      default:
        return 1;
    }
  }
}
