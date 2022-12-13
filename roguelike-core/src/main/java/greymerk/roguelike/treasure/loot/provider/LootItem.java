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

  public static boolean isSpecial(Random random, int level) {
    return random.nextInt(20 + (level * 10)) == 0;
  }

  public static boolean isEnchanted(int difficulty, Random rand, int level) {
    switch (difficulty) {
      default:
      case 0: // peaceful
        return false;
      case 1: // easy
        return rand.nextInt(6) == 0;
      case 2: // normal
        return rand.nextInt(6 - level) == 0;
      case 3: // difficult
        return rand.nextBoolean();
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
