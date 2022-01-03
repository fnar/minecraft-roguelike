package com.github.fnar.minecraft.item;

import java.util.Random;

import greymerk.roguelike.util.DyeColor;

public class Design {

  private final DesignPattern designPattern;
  private final DyeColor color;

  public Design(DesignPattern designPattern, DyeColor color) {
    this.designPattern = designPattern;
    this.color = color;
  }

  static Design randomDesign(Random random) {
    return DesignPattern.chooseRandom(random).withColor(DyeColor.chooseRandom(random));
  }

  public DesignPattern getDesignPattern() {
    return designPattern;
  }

  public DyeColor getColor() {
    return color;
  }

}
