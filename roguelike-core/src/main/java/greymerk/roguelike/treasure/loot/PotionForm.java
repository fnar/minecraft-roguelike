package greymerk.roguelike.treasure.loot;

import java.util.Random;

public enum PotionForm {

  REGULAR,
  SPLASH,
  LINGERING;

  public static PotionForm chooseRandom(Random rand) {
    return values()[rand.nextInt(values().length)];
  }
}
