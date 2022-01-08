package greymerk.roguelike.util;

import com.github.fnar.minecraft.item.Dye;

import net.minecraft.item.EnumDyeColor;

import java.util.Random;

public enum DyeColor {

  BLACK,
  BLUE,
  BROWN,
  CYAN,
  GRAY,
  GREEN,
  LIGHT_BLUE,
  LIGHT_GRAY,
  LIME,
  MAGENTA,
  ORANGE,
  PINK,
  PURPLE,
  RED,
  WHITE,
  YELLOW;

  public static DyeColor chooseRandom() {
    return values()[(int) (Math.random() * values().length)];
  }

  public static DyeColor chooseRandom(Random random) {
    return values()[random.nextInt(values().length)];
  }

  // todo: extract all calls of this in to static color objects, and map to ints
  public static int RGBToColor(int r, int g, int b) {
    return r << 16 | g << 8 | b;
  }

  public EnumDyeColor toEnumDyeColor() {
    try {
      return EnumDyeColor.valueOf(toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return EnumDyeColor.WHITE;
    }
  }

  public Dye asItem() {
    return new Dye(this);
  }
}
