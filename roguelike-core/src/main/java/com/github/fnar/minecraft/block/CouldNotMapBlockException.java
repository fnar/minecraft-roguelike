package com.github.fnar.minecraft.block;

public class CouldNotMapBlockException extends Exception {
  public CouldNotMapBlockException(String s) {
    super(s);
  }

  public CouldNotMapBlockException(String s, Exception e) {
    super(s, e);
  }
}
