package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PyramidTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord dungeon) {

    Coord floor = Tower.getBaseCoord(editor, dungeon);
    floor.translate(Cardinal.UP);
    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    start = new Coord(x - 8, floor.getY() - 1, z - 8);
    end = new Coord(x + 8, y + 10, z + 8);
    RectSolid.newRect(start, end).fill(editor, blocks);

    start = new Coord(x - 6, floor.getY() - 1, z - 6);
    end = new Coord(x + 6, floor.getY() + 3, z + 6);
    RectHollow.newRect(start, end).fill(editor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(floor);
      cursor.translate(dir, 6);
      wall(editor, theme, dir, cursor);
      cursor.translate(dir.antiClockwise(), 6);
      corner(editor, theme, dir, cursor);
    }

    // todo: Should the Entrance always be to the East?
    cursor = new Coord(floor);
    cursor.translate(Cardinal.EAST, 6);
    entrance(editor, theme, Cardinal.EAST, cursor);

    cursor = new Coord(floor);
    cursor.translate(Cardinal.UP, 4);
    spire(editor, theme, cursor);

    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

  }

  private void entrance(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    end = new Coord(start);
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, blocks);

    for (Cardinal o : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(dir);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(dir.reverse());
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, blocks);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(o, 2);
      blocks.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      blocks.stroke(editor, cursor);
    }

    // door
    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir.reverse());
    end.translate(dir);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 2);
    blocks.stroke(editor, cursor);

    // door cap
    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    start.translate(dir);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = new Coord(origin);
    cursor.translate(dir);
    cursor.translate(Cardinal.UP, 4);
    BlockType.LAPIS_BLOCK.getBrush().stroke(editor, cursor);

    cursor.translate(Cardinal.UP, 2);
    blocks.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    blocks.stroke(editor, cursor);
  }

  private void spire(WorldEditor editor, ThemeBase theme, Coord origin) {
    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // outer wall
      start = new Coord(origin);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, blocks);

      // doors
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      SingleBlockBrush.AIR.stroke(editor, cursor);

      // wall cap
      start = new Coord(origin);
      start.translate(dir, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      end.translate(dir);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(origin);
      start.translate(dir);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, blocks);

      // corner spikes
      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(origin);
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 4);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(origin);
      start.translate(dir);
      start.translate(dir.antiClockwise());
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = new Coord(origin);
      start.translate(dir);
      start.translate(Cardinal.UP, 7);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, blocks);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 7);
    end = new Coord(start);
    end.translate(Cardinal.UP, 6);
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 7);
    BlockType.GLOWSTONE.getBrush().stroke(editor, cursor);

  }

  private void wall(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord pos) {
    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    // upper wall lip
    start = new Coord(pos);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(editor, blocks);

    // inner wall
    start = new Coord(pos);
    start.translate(dir.reverse());
    end = new Coord(start);
    end.translate(dir.reverse());
    end.translate(Cardinal.UP, 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = new Coord(pos);
    cursor.translate(dir.reverse(), 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      Coord c2 = new Coord(pos);
      for (int i = 0; i < 5; ++i) {
        if (i % 2 == 0) {
          cursor = new Coord(c2);
          cursor.translate(Cardinal.UP, 5);
          blocks.stroke(editor, cursor);

          start = new Coord(c2);
          start.translate(Cardinal.UP);
          end = new Coord(start);
          end.translate(Cardinal.UP, 2);
          RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
        } else {
          cursor = new Coord(c2);
          cursor.translate(dir);
          blocks.stroke(editor, cursor);
          cursor.translate(Cardinal.UP);
          blocks.stroke(editor, cursor);
        }
        c2.translate(o);
      }

      cursor = new Coord(pos);
      cursor.translate(dir.reverse(), 2);
      cursor.translate(o, 2);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      SingleBlockBrush.AIR.stroke(editor, cursor);
    }
  }

  private void corner(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord pos) {

    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] faces = {dir, dir.antiClockwise()};

    for (Cardinal face : faces) {
      start = new Coord(pos);
      start.translate(face);
      end = new Coord(start);
      end.translate(face.antiClockwise());
      start.translate(face.clockwise());
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, blocks);

      cursor = new Coord(pos);
      cursor.translate(face, 2);
      blocks.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      blocks.stroke(editor, cursor);

      cursor = new Coord(pos);
      cursor.translate(face);
      cursor.translate(Cardinal.UP, 2);
      blocks.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      blocks.stroke(editor, cursor);
    }

    start = new Coord(pos);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, blocks);
  }


}