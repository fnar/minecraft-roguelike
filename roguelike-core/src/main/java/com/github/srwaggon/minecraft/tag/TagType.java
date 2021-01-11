package com.github.srwaggon.minecraft.tag;

public enum TagType {
  INT,
  STRING,
  COMPOUND,
  ;

  public int getTypeId() {
    return this.ordinal();
  }
}
