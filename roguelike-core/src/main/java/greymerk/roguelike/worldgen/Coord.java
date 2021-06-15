package greymerk.roguelike.worldgen;

import java.util.Objects;

import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Direction.UP;

public class Coord {

  private int x;
  private int y;
  private int z;

  public Coord(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Coord(Coord toClone) {
    this(toClone.x, toClone.y, toClone.z);
  }

  // Arranges two coords so that the they create a positive cube.
  // used in fill routines.
  public static void correct(Coord one, Coord two) {

    int temp;

    if (two.x < one.x) {
      temp = two.x;
      two.x = one.x;
      one.x = temp;
    }

    if (two.y < one.y) {
      temp = two.y;
      two.y = one.y;
      one.y = temp;
    }

    if (two.z < one.z) {
      temp = two.z;
      two.z = one.z;
      one.z = temp;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  public Coord copy() {
    return new Coord(this);
  }

  public Coord add(Direction dir) {
    return copy().translate(dir, 1);
  }

  public Coord translate(Direction dir, int amount) {
    return dir.translate(this, amount);
  }

  public Coord translate(int x, int y, int z) {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }

  public Coord translate(Coord other) {
    return translate(other.x, other.y, other.z);
  }

  public Coord translate(Direction dir) {
    translate(dir, 1);
    return this;
  }

  public Coord up() {
    return up(1);
  }

  public Coord up(int amount) {
    this.y += amount;
    return this;
  }

  public Coord down() {
    return down(1);
  }

  public Coord down(int amount) {
    this.y -= amount;
    return this;
  }

  public Coord north() {
    return north(1);
  }

  public Coord north(int amount) {
    this.z -= amount;
    return this;
  }

  public Coord east() {
    return east(1);
  }

  public Coord east(int amount) {
    this.x += amount;
    return this;
  }

  public Coord south() {
    return south(1);
  }

  public Coord south(int amount) {
    this.z += amount;
    return this;
  }

  public Coord west() {
    return west(1);
  }

  public Coord west(int amount) {
    this.x -= amount;
    return this;
  }

  public Coord add(Coord other) {
    return copy().translate(other);
  }

  public Coord add(int x, int y, int z) {
    return copy().translate(x, y, z);
  }

  public double distance(Coord other) {
    double side1 = Math.abs(getX() - other.getX());
    double side2 = Math.abs(getZ() - other.getZ());

    return Math.sqrt((side1 * side1) + (side2 * side2));
  }

  public Direction dirTo(Coord other) {
    int xdiff = other.x - x;
    int ydiff = other.y - y;
    int zdiff = other.z - z;

    if (
        Math.abs(ydiff) > Math.abs(xdiff)
            && Math.abs(ydiff) > Math.abs(zdiff)) {
      return UP;
    }


    if (Math.abs(xdiff) < Math.abs(zdiff)) {
      if (zdiff < 0) {
        return Direction.NORTH;
      } else {
        return Direction.SOUTH;
      }
    } else {
      if (xdiff < 0) {
        return Direction.WEST;
      } else {
        return Direction.EAST;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return String.format("{\"x\": %d, \"y\": %d, \"z\": %d}", x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    Coord other = (Coord) o;

    if (x != other.x) {
      return false;
    }
    if (y != other.y) {
      return false;
    }
    return z == other.z;
  }

  public RectSolid newRect(int radius) {
    Coord corner0 = copy().north(radius).east(radius);
    Coord corner1 = copy().south(radius).west(radius);
    return RectSolid.newRect(corner0, corner1);
  }

  public RectHollow newHollowRect(int radius) {
    Coord corner0 = copy().north(radius).east(radius);
    Coord corner1 = copy().south(radius).west(radius);
    return RectHollow.newRect(corner0, corner1);
  }
}
