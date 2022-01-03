package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;
import java.util.stream.IntStream;

public class Shield {

  public static RldItemStack get(Random rand) {
    com.github.fnar.minecraft.item.Shield shield = new com.github.fnar.minecraft.item.Shield();
    int count = rand.nextInt(8) + 1;
    IntStream.range(0, count)
        .forEach(i -> shield.addRandomPattern(rand));
    return shield.asStack();
  }

}
