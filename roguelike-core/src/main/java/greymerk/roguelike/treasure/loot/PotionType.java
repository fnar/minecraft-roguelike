package greymerk.roguelike.treasure.loot;

import java.util.Random;

public enum PotionType {

  AWKWARD,
  FIRE_RESISTANCE,
  HARMING,
  HEALING,
  INVISIBILITY,
  LEAPING,
  LEVITATION,
  LUCK,
  NIGHT_VISION,
  POISON,
  REGENERATION,
  SLOWNESS,
  SLOW_FALLING,
  STRENGTH,
  SWIFTNESS,
  TURTLE_MASTER,
  WATER_BREATHING,
  WEAKNESS,
  ;

  public static final PotionType[] BUFF = {HEALING, LEAPING, REGENERATION, STRENGTH, SWIFTNESS};
  public static final PotionType[] HARMFUL = {HARMING, POISON, SLOWNESS, WEAKNESS};
  public static final PotionType[] QUIRK = {FIRE_RESISTANCE, INVISIBILITY, LEVITATION, NIGHT_VISION, SLOW_FALLING, WATER_BREATHING};

  public static PotionType chooseRandom(Random random) {
    return chooseRandomAmong(random, values());
  }

  public static PotionType chooseRandomAmong(Random random, PotionType[] potionTypes) {
    return potionTypes[random.nextInt(potionTypes.length)];
  }

}
