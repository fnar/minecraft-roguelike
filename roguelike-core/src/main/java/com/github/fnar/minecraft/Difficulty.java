 package com.github.fnar.minecraft;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Difficulty {

  PEACEFUL,
  EASY,
  NORMAL,
  DIFFICULT
  ;

  public int asInt() {
    return ordinal();
  }

  public static Difficulty fromInt(int number) {
    return Arrays.stream(values()).filter((difficulty) -> difficulty.asInt() == number).findFirst().orElseThrow(() -> new NoSuchElementException("No Difficulty with numerical equivalent of " + number));
  }
}
