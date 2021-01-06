package greymerk.roguelike.worldgen;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordTest {

  @Test
  public void addNothing() {
    Coord test = new Coord(0, 0, 0);
    test.translate(new Coord(0, 0, 0));
    assertThat(test.equals(new Coord(0, 0, 0))).isTrue();

    test = new Coord(-10, 10, -10);
    test.translate(new Coord(0, 0, 0));
    assertThat(test.equals(new Coord(-10, 10, -10))).isTrue();
  }

  @Test
  public void addDirection() {
    Coord test = new Coord(0, 0, 0);
    test.translate(Direction.NORTH);
    assertThat(test.equals(new Coord(0, 0, -1))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.SOUTH);
    assertThat(test.equals(new Coord(0, 0, 1))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.WEST);
    assertThat(test.equals(new Coord(-1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.EAST);
    assertThat(test.equals(new Coord(1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.DOWN);
    assertThat(test.equals(new Coord(0, -1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.UP);
    assertThat(test.equals(new Coord(0, 1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.NORTH, 5);
    assertThat(test.equals(new Coord(0, 0, -5))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.SOUTH, 5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.NORTH, -5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.translate(Direction.SOUTH, -5);
    assertThat(test.equals(new Coord(0, 0, -5))).isTrue();
  }

  @Test
  public void addCoord() {
    Coord test;

    test = new Coord(0, 0, 0);
    test.translate(new Coord(5, 5, 5));
    assertThat(test.equals(new Coord(5, 5, 5))).isTrue();

    test = new Coord(-10, 0, 0);
    test.translate(new Coord(100, 0, 0));
    assertThat(test.equals(new Coord(90, 0, 0))).isTrue();

  }

  @Test
  public void sub() {
    Coord test;
    test = new Coord(0, 0, 0);
    test.translate(new Coord(-5, -5, -5));
    assertThat(test.equals(new Coord(-5, -5, -5))).isTrue();

    test = new Coord(100, 0, 0);
    test.translate(new Coord(-10, 0, 0));
    assertThat(test.equals(new Coord(90, 0, 0))).isTrue();

  }

  @Test
  public void dirTo() {
    Coord one = new Coord(0, 0, 0);

    Coord two = new Coord(one);
    two.translate(Direction.NORTH);
    assert (one.dirTo(two) == Direction.NORTH);

    two = new Coord(one);
    two.translate(Direction.SOUTH);
    assert (one.dirTo(two) == Direction.SOUTH);

    two = new Coord(one);
    two.translate(Direction.WEST);
    assert (one.dirTo(two) == Direction.WEST);

    two = new Coord(one);
    two.translate(Direction.EAST);
    assert (one.dirTo(two) == Direction.EAST);
  }

}
