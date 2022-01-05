package com.github.fnar.minecraft;

public class CouldNotMapException extends RuntimeException {
  public CouldNotMapException(String s) {
    super(s);
  }

  public CouldNotMapException(String s, Exception e) {
    super(s, e);
  }
}
