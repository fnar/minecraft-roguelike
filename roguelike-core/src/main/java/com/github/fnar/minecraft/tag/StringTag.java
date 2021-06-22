package com.github.fnar.minecraft.tag;

public class StringTag implements Tag {

  private String value;

  public StringTag(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public TagType getType() {
    return TagType.STRING;
  }
}
