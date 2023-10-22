package com.github.fnar.roguelike.command.message;

import greymerk.roguelike.util.TextFormat;

public enum MessageType {

  INFO(TextFormat.GRAY),
  ERROR(TextFormat.RED),
  SUCCESS(TextFormat.GREEN),
  SPECIAL(TextFormat.GOLD),
  WARNING(TextFormat.YELLOW);

  private final TextFormat textFormat;

  MessageType(TextFormat textFormat) {
    this.textFormat = textFormat;
  }

  public TextFormat getTextFormat() {
    return textFormat;
  }

  public String apply(String message) {
    return getTextFormat().apply(message);
  }
}
