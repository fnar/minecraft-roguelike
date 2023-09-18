package com.github.fnar.minecraft.item.mapper;

import greymerk.roguelike.util.DyeColor;

public class DyeColorMapper1_14 {
  public static net.minecraft.item.DyeColor toEnumDyeColor(DyeColor color) {
    try {
      return net.minecraft.item.DyeColor.valueOf(color.toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return net.minecraft.item.DyeColor.WHITE;
    }
  }
}
