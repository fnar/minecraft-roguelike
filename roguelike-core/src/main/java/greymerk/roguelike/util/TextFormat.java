package greymerk.roguelike.util;

import net.minecraft.util.text.TextFormatting;

public enum TextFormat {

  BLACK('0'),
  DARKBLUE('1'),
  DARKGREEN('2'),
  DARKAQUA('3'),
  DARKRED('4'),
  DARKPURPLE('5'),
  GOLD('6'),
  GRAY('7'),
  DARKGRAY('8'),
  BLUE('9'),
  GREEN('a'),
  AQUA('b'),
  RED('c'),
  LIGHTPURPLE('d'),
  YELLOW('e'),
  WHITE('f'),
  OBFUSCATED('k'),
  BOLD('l'),
  STRIKETHROUGH('m'),
  UNDERLINE('n'),
  ITALIC('o'),
  RESET('r'),
  ;

  private char codeChar;

  TextFormat(char codeChar) {
    this.codeChar = codeChar;
  }

  public char getCodeChar() {
    return codeChar;
  }

  public String apply(String text) {
    //return String.format("§%s%s", getCodeChar(), text);
    return "§" + getCodeChar() + "" + text;
  }

  public TextFormatting toTextFormatting(){
    if(this.codeChar == BLACK.getCodeChar())  return TextFormatting.BLACK;
    if(this.codeChar == DARKBLUE.getCodeChar())  return TextFormatting.DARK_BLUE;
    if(this.codeChar == DARKGREEN.getCodeChar())  return TextFormatting.DARK_GREEN;
    if(this.codeChar == DARKAQUA.getCodeChar())  return TextFormatting.DARK_AQUA;
    if(this.codeChar == DARKRED.getCodeChar())  return TextFormatting.DARK_RED;
    if(this.codeChar == DARKPURPLE.getCodeChar())  return TextFormatting.DARK_PURPLE;
    if(this.codeChar == GOLD.getCodeChar())  return TextFormatting.GOLD;
    if(this.codeChar == GRAY.getCodeChar())  return TextFormatting.GRAY;
    if(this.codeChar == DARKGRAY.getCodeChar())  return TextFormatting.DARK_GRAY;
    if(this.codeChar == BLUE.getCodeChar())  return TextFormatting.BLUE;
    if(this.codeChar == GREEN.getCodeChar())  return TextFormatting.GREEN;
    if(this.codeChar == AQUA.getCodeChar())  return TextFormatting.AQUA;
    if(this.codeChar == RED.getCodeChar())  return TextFormatting.RED;
    if(this.codeChar == LIGHTPURPLE.getCodeChar())  return TextFormatting.LIGHT_PURPLE;
    if(this.codeChar == YELLOW.getCodeChar())  return TextFormatting.YELLOW;
    if(this.codeChar == WHITE.getCodeChar())  return TextFormatting.WHITE;
    if(this.codeChar == OBFUSCATED.getCodeChar())  return TextFormatting.OBFUSCATED;
    if(this.codeChar == BOLD.getCodeChar())  return TextFormatting.BOLD;
    if(this.codeChar == STRIKETHROUGH.getCodeChar())  return TextFormatting.STRIKETHROUGH;
    if(this.codeChar == UNDERLINE.getCodeChar())  return TextFormatting.UNDERLINE;
    if(this.codeChar == ITALIC.getCodeChar())  return TextFormatting.ITALIC;
    return TextFormatting.RESET;
  }

}


