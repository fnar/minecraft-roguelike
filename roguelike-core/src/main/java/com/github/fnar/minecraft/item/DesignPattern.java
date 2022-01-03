package com.github.fnar.minecraft.item;

import java.util.Random;

import greymerk.roguelike.util.DyeColor;

public enum DesignPattern {

  BASE,
  BORDER,
  BRICKS,
  CIRCLE_MIDDLE,
  CREEPER,
  CROSS,
  CURLY_BORDER,
  DIAGONAL_LEFT,
  DIAGONAL_LEFT_MIRROR,
  DIAGONAL_RIGHT,
  DIAGONAL_RIGHT_MIRROR,
  FLOWER,
  GRADIENT,
  GRADIENT_UP,
  HALF_HORIZONTAL,
  HALF_HORIZONTAL_MIRROR,
  HALF_VERTICAL,
  HALF_VERTICAL_MIRROR,
  MOJANG,
  RHOMBUS_MIDDLE,
  SKULL,
  SQUARE_BOTTOM_LEFT,
  SQUARE_BOTTOM_RIGHT,
  SQUARE_TOP_LEFT,
  SQUARE_TOP_RIGHT,
  STRAIGHT_CROSS,
  STRIPE_BOTTOM,
  STRIPE_CENTER,
  STRIPE_DOWNLEFT,
  STRIPE_DOWNRIGHT,
  STRIPE_LEFT,
  STRIPE_MIDDLE,
  STRIPE_RIGHT,
  STRIPE_SMALL,
  STRIPE_TOP,
  TRIANGLES_BOTTOM,
  TRIANGLES_TOP,
  TRIANGLE_BOTTOM,
  TRIANGLE_TOP,
  ;

  public static DesignPattern chooseRandom() {
    return values()[(int) (Math.random() * values().length)];
  }

  public static DesignPattern chooseRandom(Random random) {
    return values()[random.nextInt(values().length)];
  }

  public Design withColor(DyeColor color) {
    return new Design(this, color);
  }

  public net.minecraft.tileentity.BannerPattern toBannerPattern() {
    try {
      return net.minecraft.tileentity.BannerPattern.valueOf(toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return net.minecraft.tileentity.BannerPattern.BASE;
    }
  }
}
