package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Exceptions;

import java.util.Random;

import greymerk.roguelike.util.IWeighted;

public abstract class LootItem implements IWeighted<RldItemStack> {

  int level;
  private final int weight;

  public LootItem(int weight, int level) {
    this.weight = weight;
    this.level = level;
  }

  public abstract RldItemStack getLootItem(Random random);

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public RldItemStack get(Random rand) {
    try {
      return getLootItem(rand);
    } catch (Exception e) {
      e.printStackTrace();
      return Material.Type.PAPER.asItemStack()
          .withDisplayName("Report: Loot Item Generation Failure")
          .withDisplayLore(
              e.getClass().getName(),
              Exceptions.asString(e),
              "Check server logs for details",
              "Consider submitting to https://github.com/fnar/minecraft-roguelike/issues"
          );
    }
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
