package com.github.fnar.roguelike.command;

import net.minecraft.util.text.TextFormatting;

import greymerk.roguelike.util.TextFormat;

class TextFormattingMapper1_12 {

  public static TextFormatting map(char codeChar) {
    if (codeChar == TextFormat.BLACK.getCodeChar()) {
      return TextFormatting.BLACK;
    }
    if (codeChar == TextFormat.DARKBLUE.getCodeChar()) {
      return TextFormatting.DARK_BLUE;
    }
    if (codeChar == TextFormat.DARKGREEN.getCodeChar()) {
      return TextFormatting.DARK_GREEN;
    }
    if (codeChar == TextFormat.DARKAQUA.getCodeChar()) {
      return TextFormatting.DARK_AQUA;
    }
    if (codeChar == TextFormat.DARKRED.getCodeChar()) {
      return TextFormatting.DARK_RED;
    }
    if (codeChar == TextFormat.DARKPURPLE.getCodeChar()) {
      return TextFormatting.DARK_PURPLE;
    }
    if (codeChar == TextFormat.GOLD.getCodeChar()) {
      return TextFormatting.GOLD;
    }
    if (codeChar == TextFormat.GRAY.getCodeChar()) {
      return TextFormatting.GRAY;
    }
    if (codeChar == TextFormat.DARKGRAY.getCodeChar()) {
      return TextFormatting.DARK_GRAY;
    }
    if (codeChar == TextFormat.BLUE.getCodeChar()) {
      return TextFormatting.BLUE;
    }
    if (codeChar == TextFormat.GREEN.getCodeChar()) {
      return TextFormatting.GREEN;
    }
    if (codeChar == TextFormat.AQUA.getCodeChar()) {
      return TextFormatting.AQUA;
    }
    if (codeChar == TextFormat.RED.getCodeChar()) {
      return TextFormatting.RED;
    }
    if (codeChar == TextFormat.LIGHTPURPLE.getCodeChar()) {
      return TextFormatting.LIGHT_PURPLE;
    }
    if (codeChar == TextFormat.YELLOW.getCodeChar()) {
      return TextFormatting.YELLOW;
    }
    if (codeChar == TextFormat.WHITE.getCodeChar()) {
      return TextFormatting.WHITE;
    }
    if (codeChar == TextFormat.OBFUSCATED.getCodeChar()) {
      return TextFormatting.OBFUSCATED;
    }
    if (codeChar == TextFormat.BOLD.getCodeChar()) {
      return TextFormatting.BOLD;
    }
    if (codeChar == TextFormat.STRIKETHROUGH.getCodeChar()) {
      return TextFormatting.STRIKETHROUGH;
    }
    if (codeChar == TextFormat.UNDERLINE.getCodeChar()) {
      return TextFormatting.UNDERLINE;
    }
    if (codeChar == TextFormat.ITALIC.getCodeChar()) {
      return TextFormatting.ITALIC;
    }
    return TextFormatting.RESET;
  }
}
