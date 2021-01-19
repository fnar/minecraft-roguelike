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
    test.north();
    assertThat(test.equals(new Coord(0, 0, -1))).isTrue();

    test = new Coord(0, 0, 0);
    test.south();
    assertThat(test.equals(new Coord(0, 0, 1))).isTrue();

    test = new Coord(0, 0, 0);
    test.west();
    assertThat(test.equals(new Coord(-1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.east();
    assertThat(test.equals(new Coord(1, 0, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.down();
    assertThat(test.equals(new Coord(0, -1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.up();
    assertThat(test.equals(new Coord(0, 1, 0))).isTrue();

    test = new Coord(0, 0, 0);
    test.north(5);
    assertThat(test.equals(new Coord(0, 0, -5))).isTrue();

    test = new Coord(0, 0, 0);
    test.south(5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.north(-5);
    assertThat(test.equals(new Coord(0, 0, 5))).isTrue();

    test = new Coord(0, 0, 0);
    test.south(-5);
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
    two.north();
    assert (one.dirTo(two) == Direction.NORTH);

    two = new Coord(one);
    two.south();
    assert (one.dirTo(two) == Direction.SOUTH);

    two = new Coord(one);
    two.west();
    assert (one.dirTo(two) == Direction.WEST);

    two = new Coord(one);
    two.east();
    assert (one.dirTo(two) == Direction.EAST);
  }

}
