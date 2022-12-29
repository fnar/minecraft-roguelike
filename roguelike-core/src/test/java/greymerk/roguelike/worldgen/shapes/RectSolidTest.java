package greymerk.roguelike.worldgen.shapes;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RectSolidTest {

  @Mock
  private WorldEditor worldEditor;
  @Captor
  private ArgumentCaptor<Coord> coordCaptor;

  @Test
  public void fillCanFillUpwards() {
    Coord start = new Coord(0, 0, 0);
    Coord end = new Coord(10, 10, 10);
    RectSolid.newRect(start, end).fill(worldEditor, BlockType.EMERALD_BLOCK.getBrush());

    assertAllSpacesAreFilledIn(start, end);
  }

  @Test
  public void newRectWithRadius() {
    List<Coord> expectedCoords = IntStream.range(-2, 3)
        .mapToObj(finalZ -> IntStream.range(-2, 3).mapToObj(x -> new Coord(x, 0, finalZ)).collect(Collectors.toList()))
        .flatMap(List::stream)
        .collect(Collectors.toList());

    RectSolid actual = new Coord(0, 0, 0).newRect(3);

    assertThat(actual.withHeight(0).get().size()).isEqualTo(25);
    assertThat(actual.withHeight(0).get()).containsExactly(expectedCoords.toArray(new Coord[]{}));
  }

  @Test
  public void withHeight0ContainsSinglePoint() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(0).get().size()).isEqualTo(1);
  }

  @Test
  public void withHeight0ContainsOriginalCoord() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(0).get()).containsExactly(new Coord(0, 0, 0));
  }

  @Test
  public void withHeight1ContainsSinglePoint() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(1).get().size()).isEqualTo(1);
  }

  @Test
  public void withHeight1ContainsOriginalCoord() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(1).get()).containsExactly(new Coord(0, 0, 0));
  }

  @Test
  public void withHeight10Contains10Points() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(10).get().size()).isEqualTo(10);
  }

  @Test
  public void twoRectanglesAreTheSame() {
    Coord origin = new Coord(0, 0, 0);
    RectSolid r0 = origin.newRect(2);
    RectSolid r1 = origin.newRect(2).withHeight(1);
    assertThat(r0).isEqualTo(r1);
  }

  @Test
  public void iteratorsProvideEqualValuesWhenHeightIsUsed() {
    Coord origin = new Coord(0, 0, 0);
    RectSolid r0 = origin.newRect(2);
    RectSolid r1 = origin.newRect(2).withHeight(1);
    asserThatIteratorsAreEqual(r0, r1);
    asserThatIteratorsAreEqual(r1, r0);
  }

  private void asserThatIteratorsAreEqual(RectSolid r0, RectSolid r1) {
    Iterator<Coord> r0Iterator = r0.iterator();
    Iterator<Coord> r1Iterator = r1.iterator();

    while (r0Iterator.hasNext()) {
      Coord c = r0Iterator.next();
      Coord d = r1Iterator.next();
      assertThat(c).isEqualTo(d);
    }
    assertThat(r1Iterator.hasNext()).isFalse();
  }

  @Test
  public void asListProvidesEqualCoordsWhenHeightIsUsed() {
    Coord origin = new Coord(0, 0, 0);
    RectSolid r0 = origin.newRect(2);
    RectSolid r1 = origin.newRect(2).withHeight(1);
    assertThat(r0.asList()).isEqualTo(r1.asList());
    assertThat(r1.asList()).isEqualTo(r0.asList());
  }

  @Test
  public void withHeight10Contains10VerticalPoints() {
    assertThat(new Coord(0, 0, 0).newRect(0).withHeight(10).get()).containsExactly(
        new Coord(0, 0, 0),
        new Coord(0, 1, 0),
        new Coord(0, 2, 0),
        new Coord(0, 3, 0),
        new Coord(0, 4, 0),
        new Coord(0, 5, 0),
        new Coord(0, 6, 0),
        new Coord(0, 7, 0),
        new Coord(0, 8, 0),
        new Coord(0, 9, 0)
    );
  }

  @Test
  public void fillCanFillDownwards() {
    Coord start = new Coord(10, 10, 10);
    Coord end = new Coord(0, 0, 0);
    RectSolid.newRect(start, end).fill(worldEditor, BlockType.EMERALD_BLOCK.getBrush());

    assertAllSpacesAreFilledIn(start, end);
  }

  public void assertAllSpacesAreFilledIn(Coord start, Coord end) {
    int dx = Math.abs(end.getX() - start.getX()) + 1;
    int dy = Math.abs(end.getY() - start.getY()) + 1;
    int dz = Math.abs(end.getZ() - start.getZ()) + 1;
    verify(worldEditor, times(dx * dy * dz)).setBlock(coordCaptor.capture(), any(SingleBlockBrush.class), anyBoolean(), anyBoolean());

    for (int z = 0; z <= 10; z++) {
      for (int y = 0; y <= 10; y++) {
        for (int x = 0; x <= 10; x++) {
          Coord actual = coordCaptor.getAllValues().get((y * dz * dx) + (z * dx) + x);
          assertThat(actual).isEqualTo(new Coord(x, y, z));
        }
      }
    }
  }
}
