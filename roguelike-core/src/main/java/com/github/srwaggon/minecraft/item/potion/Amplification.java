package com.github.srwaggon.minecraft.item.potion;

import java.util.Random;

public enum Amplification {

  UNAMPLIFIED(0),
  LEVEL_ONE(1),
  LEVEL_TWO(2);

  private final int level;

  Amplification(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public static Amplification chooseRandom(Random random) {
    return values()[random.nextInt(values().length)];
  }
}
