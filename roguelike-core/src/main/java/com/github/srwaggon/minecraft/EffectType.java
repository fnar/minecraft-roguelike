package com.github.srwaggon.minecraft;

import java.util.Random;

public enum EffectType {

  SPEED(1),
  SLOWNESS(2),
  HASTE(3),
  FATIGUE(4),
  STRENGTH(5),
  HEALTH(6),
  DAMAGE(7),
  JUMP(8),
  NAUSEA(9),
  REGEN(10),
  RESISTANCE(11),
  FIRE_RESISTANCE(12),
  WATER_BREATHING(13),
  INVISIBILITY(14),
  BLINDNESS(15),
  NIGHT_VISION(16),
  HUNGER(17),
  WEAKNESS(18),
  POISON(19),
  WITHER(20),
  HEALTH_BOOST(21),
  ABSORPTION(22),
  SATURATION(23),
  GLOWING(24),
  LEVITATION(25),
  LUCK(26),
  BAD_LUCK(27),
  SLOW_FALLING(28),
  CONDUIT_POWER(29),
  DOLPHINS_GRACE(30),
  BAD_OMEN(31),
  HERO_OF_THE_VILLAGE(32),
  ;

  private final int id;

  EffectType(int id) {
    this.id = id;
  }

  public int getEffectID() {
    return id;
  }

  public static EffectType chooseRandom(Random random) {
    return values()[random.nextInt(values().length)];
  }

}
