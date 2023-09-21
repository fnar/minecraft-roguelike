package greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.worldgen.Coord;

public class ArgumentParser {

  private final List<String> args = new ArrayList<>();

  public ArgumentParser(List<String> args) {
    this.args.addAll(args);
  }

  public int getInt(int index) {
    return parseInteger(get(index));
  }

  public String get(int index) {
    return this.hasEntry(index) ? this.args.get(index) : null;
  }

  private int parseInteger(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException numberFormatException) {
      throw new RuntimeException("Could not parse input as number: " + input);
    }
  }

  public Coord getCoord(int argIndex) {
    if (hasEntry(argIndex)) {
      int arg1 = this.getInt(argIndex);
      int arg2 = this.getInt(argIndex + 1);

      if (!hasEntry(argIndex + 2)) {
        return new Coord(arg1, 0, arg2);
      } else {
        int arg3 = this.getInt(argIndex + 2);
        return new Coord(arg1, arg2, arg3);
      }
    }
    // todo: internationalization
    throw new IllegalArgumentException(String.format("Could not map arguments to coord: %s.", this));
  }

  public boolean hasEntry(int index) {
    return index < this.args.size();
  }

  public boolean match(int index, String toCompare) {
    return this.hasEntry(index) && this.args.get(index).equals(toCompare);
  }

  @Override
  public String toString() {
    return this.args.toString();
  }
}
