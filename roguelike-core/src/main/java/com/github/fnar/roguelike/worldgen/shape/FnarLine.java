package com.github.fnar.roguelike.worldgen.shape;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.shapes.IShape;

public class FnarLine implements IShape {
  private final Coord start;
  private final Coord end;

  public FnarLine(Coord start, Coord end) {
    this.start = start.copy();
    this.end = end.copy();
  }

  @Override
  public List<Coord> get() {
    return Lists.newArrayList(start.copy(), end.copy());
  }

  @Override
  public Set<Coord> getAnchors() {
    return Sets.newHashSet(start.copy(), end.copy());
  }

  @Nonnull
  @Override
  public Iterator<Coord> iterator() {
    return new FnarLineIterator(start.copy(), end.copy());
  }

  static class FnarLineIterator implements Iterator<Coord> {

    private Coord next;
    private Coord end;

    public FnarLineIterator(Coord next, Coord end) {
      this.next = next;
      this.end = end;
    }

    @Override
    public boolean hasNext() {
      return end != null;
    }

    @Override
    public Coord next() {
      Coord result = next.copy();

      next = findNext();

      if (result.equals(end)) {
        end = null;
      }

      return result;
    }

    private Coord findNext() {
      int xDiff = end.getX() - next.getX();
      int absXDiff = Math.abs(xDiff);
      int xIncrement = xDiff != 0 ? xDiff / absXDiff : 0;

      int yDiff = end.getY() - next.getY();
      int absYDiff = Math.abs(yDiff);
      int yIncrement = yDiff != 0 ? yDiff / absYDiff : 0;

      int zDiff = end.getZ() - next.getZ();
      int absZDiff = Math.abs(zDiff);
      int zIncrement = zDiff != 0 ? zDiff / absZDiff : 0;

      if (absXDiff >= absYDiff && absXDiff >= absZDiff) {
        yIncrement = 0;
        zIncrement = 0;
      } else {
        xIncrement = 0;
        if (absYDiff >= absXDiff && absYDiff >= absZDiff) {
          zIncrement = 0;
        } else {
          yIncrement = 0;
        }
      }

      return next.copy().translate(new Coord(xIncrement, yIncrement, zIncrement));
    }
  }
}
