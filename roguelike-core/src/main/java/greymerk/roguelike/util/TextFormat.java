package greymerk.roguelike.util;

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

  public String toString() {
    //return "§" + codeChar; //this shows a weird character at the beginning of the formatting
    return "\u00A7" + codeChar;
  }

  public String apply(String text) {
    //return "§" + getCodeChar() + "" + text;
    return this.toString() + text;
  }

}


