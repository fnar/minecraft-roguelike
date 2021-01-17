package com.github.srwaggon.util;

import java.util.Random;

public class Color {
  public static final int BOUND = 256;
  int red;
  int green;
  int blue;

  public Color(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public static Color random() {
    Random random = new Random();
    return new Color(
        random.nextInt(BOUND),
        random.nextInt(BOUND),
        random.nextInt(BOUND)
    );
  }

  public static Color random(Random random) {
    return new Color(
        random.nextInt(BOUND),
        random.nextInt(BOUND),
        random.nextInt(BOUND)
    );
  }

  public int asInt() {
    return red << 16 | green << 8 | blue;
  }
}
