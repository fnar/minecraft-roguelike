package greymerk.roguelike.worldgen;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectionTest {

  @Test
  public void clockwise() {
    assertThat(Direction.NORTH.clockwise()).isEqualTo(Direction.EAST);
    assertThat(Direction.EAST.clockwise()).isEqualTo(Direction.SOUTH);
    assertThat(Direction.SOUTH.clockwise()).isEqualTo(Direction.WEST);
    assertThat(Direction.WEST.clockwise()).isEqualTo(Direction.NORTH);

    assertThat(Direction.UP.clockwise()).isEqualTo(Direction.UP);
    assertThat(Direction.DOWN.clockwise()).isEqualTo(Direction.DOWN);
  }

  @Test
  public void antiClockwise() {
    assertThat(Direction.NORTH.antiClockwise()).isEqualTo(Direction.WEST);
    assertThat(Direction.EAST.antiClockwise()).isEqualTo(Direction.NORTH);
    assertThat(Direction.SOUTH.antiClockwise()).isEqualTo(Direction.EAST);
    assertThat(Direction.WEST.antiClockwise()).isEqualTo(Direction.SOUTH);

    assertThat(Direction.UP.antiClockwise()).isEqualTo(Direction.UP);
    assertThat(Direction.DOWN.antiClockwise()).isEqualTo(Direction.DOWN);
  }

  @Test
  public void orthogonals() {
    assertThat(Direction.NORTH.orthogonals()).isEqualTo(new Direction[]{Direction.WEST, Direction.EAST});
    assertThat(Direction.EAST.orthogonals()).isEqualTo(new Direction[]{Direction.NORTH, Direction.SOUTH});
    assertThat(Direction.SOUTH.orthogonals()).isEqualTo(new Direction[]{Direction.EAST, Direction.WEST});
    assertThat(Direction.WEST.orthogonals()).isEqualTo(new Direction[]{Direction.SOUTH, Direction.NORTH});
  }
}