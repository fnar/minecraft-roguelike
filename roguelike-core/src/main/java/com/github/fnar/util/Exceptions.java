package com.github.fnar.util;

import java.util.Optional;

public class Exceptions {
  public static String asString(Exception e) {
    return Optional.ofNullable(e.getLocalizedMessage())
        .orElse(Optional.ofNullable(e.getMessage())
            .orElse(e.toString()));
  }
}
