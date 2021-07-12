package com.github.fnar.minecraft.worldgen.shape;

import org.junit.Test;

import java.util.Iterator;

import greymerk.roguelike.worldgen.Coord;

import static org.assertj.core.api.Assertions.assertThat;

public class FnarLineTest {

  public static final Coord ORIGIN = new Coord(0, 0, 0);
  public static final Coord X_OFFSET_COORD = new Coord(10, 0, 0);
  public static final Coord Y_OFFSET_COORD = new Coord(0, 10, 0);
  public static final Coord Z_OFFSET_COORD = new Coord(0, 0, 10);
  public static final Coord ALL_OFFSET_COORD = new Coord(10, 10, 10);
  public static final Coord ALL_NEGATIVE_OFFSET_COORD = new Coord(-10, -10, -10);

  @Test
  public void iteratorCanTraverseX() {
    FnarLine xLine = new FnarLine(ORIGIN, X_OFFSET_COORD);
    Iterator<Coord> xLineIterator = xLine.iterator();

    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(1, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(2, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(3, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(4, 0, 0));
    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(5, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(6, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(7, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(8, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(9, 0, 0));
    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(10, 0, 0));
    assertThat(xLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseXNegatively() {
    FnarLine xLine = new FnarLine(X_OFFSET_COORD, ORIGIN);
    Iterator<Coord> xLineIterator = xLine.iterator();

    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(10, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(9, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(8, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(7, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(6, 0, 0));
    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(5, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(4, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(3, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(2, 0, 0));
    assertThat(xLineIterator.next()).isEqualTo(new Coord(1, 0, 0));
    assertThat(xLineIterator.hasNext()).isTrue();
    assertThat(xLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(xLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseY() {
    FnarLine yLine = new FnarLine(ORIGIN, Y_OFFSET_COORD);
    Iterator<Coord> yLineIterator = yLine.iterator();

    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 1, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 2, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 3, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 4, 0));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 5, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 6, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 7, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 8, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 9, 0));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 10, 0));
    assertThat(yLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseYNegatively() {
    FnarLine yLine = new FnarLine(Y_OFFSET_COORD, ORIGIN);
    Iterator<Coord> yLineIterator = yLine.iterator();

    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 10, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 9, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 8, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 7, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 6, 0));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 5, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 4, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 3, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 2, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 1, 0));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(yLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseZ() {
    FnarLine yLine = new FnarLine(ORIGIN, Z_OFFSET_COORD);
    Iterator<Coord> yLineIterator = yLine.iterator();

    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 1));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 2));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 3));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 4));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 5));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 6));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 7));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 8));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 9));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 10));
    assertThat(yLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseZNegatively() {
    FnarLine yLine = new FnarLine(Z_OFFSET_COORD, ORIGIN);
    Iterator<Coord> yLineIterator = yLine.iterator();

    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 10));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 9));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 8));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 7));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 6));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 5));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 4));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 3));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 2));
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 1));
    assertThat(yLineIterator.hasNext()).isTrue();
    assertThat(yLineIterator.next()).isEqualTo(new Coord(0, 0, 0));
    assertThat(yLineIterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanTraverseAllThreeSimultaneously() {
    FnarLine line = new FnarLine(ORIGIN, ALL_OFFSET_COORD);
    Iterator<Coord> iterator = line.iterator();

    assertThat(iterator.hasNext()).isTrue();
    int expectedElements = 1 +
        ALL_OFFSET_COORD.getX() +
        ALL_OFFSET_COORD.getY() +
        ALL_OFFSET_COORD.getZ();
    for (int i = 0; i < expectedElements; i++) {
      assertThat(iterator.hasNext()).isTrue();
    }
    assertThat(iterator.hasNext()).isFalse();
  }

  @Test
  public void iteratorCanGoExploreNegativeNumbers() {
    FnarLine line = new FnarLine(ALL_OFFSET_COORD, ALL_NEGATIVE_OFFSET_COORD);
    Iterator<Coord> iterator = line.iterator();

    assertThat(iterator.hasNext()).isTrue();
    int expectedElements = 1 +
        Math.abs(ALL_NEGATIVE_OFFSET_COORD.getX()) +
        Math.abs(ALL_NEGATIVE_OFFSET_COORD.getY()) +
        Math.abs(ALL_NEGATIVE_OFFSET_COORD.getZ()) +
        ALL_OFFSET_COORD.getX() +
        ALL_OFFSET_COORD.getY() +
        ALL_OFFSET_COORD.getZ();

    for (int i = 0; i < expectedElements; i++) {
      assertThat(iterator.hasNext()).isTrue();
    }
    assertThat(iterator.hasNext()).isFalse();
  }
}
