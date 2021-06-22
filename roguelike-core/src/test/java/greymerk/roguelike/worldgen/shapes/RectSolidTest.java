package greymerk.roguelike.worldgen.shapes;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
