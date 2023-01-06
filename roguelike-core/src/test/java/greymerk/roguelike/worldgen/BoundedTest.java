package greymerk.roguelike.worldgen;

import org.junit.Test;

import greymerk.roguelike.worldgen.shapes.RectSolid;

import static org.assertj.core.api.Assertions.assertThat;

public class BoundedTest {

  @Test
  public void collide1D() {
    Bounded box = RectSolid.newRect(new Coord(6, 0, 0), new Coord(9, 0, 0));
    Bounded box2 = RectSolid.newRect(new Coord(1, 0, 0), new Coord(4, 0, 0));

    assertThat(box.collide(box2)).isFalse();

    box = RectSolid.newRect(new Coord(4, 0, 0), new Coord(9, 0, 0));
    box2 = RectSolid.newRect(new Coord(1, 0, 0), new Coord(6, 0, 0));

    assertThat(box.collide(box2)).isTrue();
  }

  @Test
  public void collide2D() {
    Bounded box = RectSolid.newRect(new Coord(1, 0, 1), new Coord(3, 0, 3));
    Bounded box2 = RectSolid.newRect(new Coord(2, 0, 2), new Coord(4, 0, 4));

    assertThat(box.collide(box2)).isTrue();
  }

  @Test
  public void collide3D() {
    Bounded box = RectSolid.newRect(new Coord(1, 1, 1), new Coord(3, 5, 3));
    Bounded box2 = RectSolid.newRect(new Coord(2, 3, 2), new Coord(4, 7, 4));

    assertThat(box.collide(box2)).isTrue();
  }

}
