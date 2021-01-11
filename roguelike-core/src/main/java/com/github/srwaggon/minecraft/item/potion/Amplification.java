package com.github.srwaggon.minecraft.item.potion;

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
}
