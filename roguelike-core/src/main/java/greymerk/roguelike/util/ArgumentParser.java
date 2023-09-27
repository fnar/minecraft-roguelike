package greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.worldgen.Coord;

public class ArgumentParser {

  private final List<String> args = new ArrayList<>();

  public ArgumentParser(List<String> args) {
    this.args.addAll(args);
  }

  @Deprecated
  public int getInt(int index) {
    if (!this.hasEntry(index)) {
      throw new IllegalArgumentException(String.format("Missing argument. No input provided at index %s.", index));
    }
    return getParseInt(get(index));
  }

  @Deprecated
  private static int getParseInt(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException numberFormatException) {
      throw new IllegalArgumentException(String.format("Could not parse argument into a valid number: \"%s\".", s));
    }
  }

  @Deprecated
  public String get(int index) {
    return this.hasEntry(index) ? this.args.get(index) : null;
  }

  @Deprecated
  public Coord getXZCoord(int index) {
    if (!hasEntry(index) || !hasEntry(index + 1)) {
      throw new IllegalArgumentException("Need strictly two coordinates: <X> <Z>");
    }

    int arg1 = this.getInt(index);
    int arg2 = this.getInt(index + 1);

    return new Coord(arg1, 0, arg2);
  }

  @Deprecated
  public Coord getCoord(int index) {
    if (!hasEntry(index) || !hasEntry(index + 1)) {
      throw new IllegalArgumentException("Need at least two coordinates: <X> <Z> | <X> <Y> <Z>");
    }

    int arg1 = this.getInt(index);
    int arg2 = this.getInt(index + 1);

    if (!hasEntry(index + 2)) {
      return new Coord(arg1, 0, arg2);
    }
    int arg3 = this.getInt(index + 2);
    return new Coord(arg1, arg2, arg3);
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
