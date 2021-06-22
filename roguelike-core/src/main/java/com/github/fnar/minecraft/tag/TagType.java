package com.github.fnar.minecraft.tag;

public enum TagType {
  INT,
  STRING,
  COMPOUND,
  LIST,
  ;

  public int getTypeId() {
    return this.ordinal();
  }
}
