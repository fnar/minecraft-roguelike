package com.github.fnar.minecraft.tag;

import lombok.ToString;

@ToString
public class StringTag implements Tag {

  private final String value;

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
