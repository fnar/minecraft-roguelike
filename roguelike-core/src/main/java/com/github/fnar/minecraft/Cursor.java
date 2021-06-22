package com.github.fnar.minecraft;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public class Cursor extends Coord {

  private Direction facing = Direction.NORTH;

  public Cursor(int x, int y, int z, Direction facing) {
    this(x, y, z);
    this.facing = facing;
  }

  public Cursor(int x, int y, int z) {
    super(x, y, z);
  }

  public Cursor(Coord toClone) {
    super(toClone);
  }

  public Cursor face(Direction direction) {
    this.facing = direction;
    return this;
  }

  public Direction getFacing() {
    return facing;
  }

  public Cursor forward() {
    return forward(1);
  }

  public Cursor forward(int n) {
    translate(facing, n);
    return this;
  }

}
