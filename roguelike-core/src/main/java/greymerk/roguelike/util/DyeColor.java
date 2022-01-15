package greymerk.roguelike.util;

import com.github.fnar.minecraft.item.Dye;

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

  public Dye asItem() {
    return new Dye(this);
  }
}
