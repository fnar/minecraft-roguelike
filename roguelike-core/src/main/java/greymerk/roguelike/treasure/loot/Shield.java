package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

public class Shield {

  public static RldItemStack get(Random rand) {
    return com.github.fnar.minecraft.item.Shield.newShield()
        .withRandomPatterns(rand, 1 + rand.nextInt(8) + 1)
        .asStack();
  }

}
