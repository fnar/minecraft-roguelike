package com.github.fnar.util;

import java.util.Random;

public class Color {

  public static final int BOUND = 256;

  // Thanks to https://colornamer.robertcooper.me/ for the names
  public static final Color PRUSSIAN_BLUE = new Color(0, 51, 102);
  public static final Color LEAD = new Color(32, 32, 32);
  public static final Color DEEP_VIOLET = new Color(51, 0, 102);
  public static final Color HARISSA_RED = new Color(165, 42, 42);
  public static final Color PALE_LIME_GREEN = new Color(178, 255, 102);
  public static final Color CHINESE_RED = new Color(250, 96, 128);
  public static final Color SMOKED_SALMON = new Color(250, 128, 114);
  public static final Color PINK_FLAMINGO = new Color(255, 100, 255);
  public static final Color CLASSIC_ROSE = new Color(255, 204, 229);
  public static final Color POPCORN = new Color(250, 220, 140);
  public static final Color SPLIT_PEA_SOUP = new Color(200, 180, 100);
  public static final Color ICE_FISHING = new Color(222, 238, 244);
  public static final Color ADVERTISING_GREEN = new Color(85, 165, 120);
  public static final Color BLUE = new Color(0, 0, 255);
  public static final Color WHITE = new Color(255, 255, 255);
  public static final Color ENGLISH_BREAKFAST = new Color(62, 14, 16);
  public static final Color SUNNY_MOOD = new Color(245, 200, 71);
  public static final Color COFFEE = new Color(111, 78, 55);
  public static final Color DELAYED_YELLOW = new Color(250, 250, 0);

  private final int red;
  private final int green;
  private final int blue;

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

  public static Color HSLToColor(float h, float s, float l) {
    float r, g, b;

    if (s == 0f) {
      r = g = b = l;
    } else {
      float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
      float p = 2 * l - q;
      r = hueToRgb(p, q, h + 1f / 3f);
      g = hueToRgb(p, q, h);
      b = hueToRgb(p, q, h - 1f / 3f);
    }
    return new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
  }

  private static float hueToRgb(float p, float q, float t) {
    if (t < 0f) {
      t += 1f;
    }
    if (t > 1f) {
      t -= 1f;
    }
    if (t < 1f / 6f) {
      return p + (q - p) * 6f * t;
    }
    if (t < 1f / 2f) {
      return q;
    }
    if (t < 2f / 3f) {
      return p + (q - p) * (2f / 3f - t) * 6f;
    }
    return p;
  }

  public int asInt() {
    return red << 16 | green << 8 | blue;
  }
}
