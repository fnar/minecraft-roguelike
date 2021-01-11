package com.github.srwaggon.minecraft.tag;

public class IntTag implements Tag {

  private final int value;

  public IntTag(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public TagType getType() {
    return TagType.INT;
  }

}
