package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PyramidTower extends Tower {

  public PyramidTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord dungeon) {

    Coord floor = TowerType.getBaseCoord(editor, dungeon);
    floor.up();

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    Coord start = new Coord(x - 8, floor.getY() - 1, z - 8);
    Coord end = new Coord(x + 8, y + 10, z + 8);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    start = new Coord(x - 6, floor.getY() - 1, z - 6);
    end = new Coord(x + 6, floor.getY() + 3, z + 6);
    RectHollow.newRect(start, end).fill(editor, getPrimaryWall());

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      cursor = floor.copy();
      cursor.translate(dir, 6);
      wall(dir, cursor);
      cursor.translate(dir.antiClockwise(), 6);
      corner(dir, cursor);
    }

    // todo: Should the Entrance always be to the East?
    cursor = floor.copy();
    cursor.east(6);
    entrance(Direction.EAST, cursor);

    cursor = floor.copy();
    cursor.up(4);
    spire(cursor);

    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(editor.getRandom(), new Coord(x, i, z), getPrimaryStair(), getPrimaryPillar());
    }

  }

  private void entrance(Direction dir, Coord origin) {

    Coord start = origin.copy();
    start.up(3);
    Coord end = start.copy();
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    Coord cursor;
    for (Direction o : dir.orthogonals()) {
      start = origin.copy();
      start.translate(dir);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir.reverse());
      end.up(3);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(o, 2);
      getPrimaryWall().stroke(editor, cursor);
      cursor.up();
      getPrimaryWall().stroke(editor, cursor);
    }

    // door
    start = origin.copy();
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    end.up();
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(dir);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.up(2);
    getPrimaryWall().stroke(editor, cursor);

    // door cap
    start = origin.copy();
    start.up(3);
    start.translate(dir);
    end = start.copy();
    end.up(2);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.translate(dir);
    cursor.up(4);
    BlockType.LAPIS_BLOCK.getBrush().stroke(editor, cursor);

    cursor.up(2);
    getPrimaryWall().stroke(editor, cursor);
    cursor.up();
    getPrimaryWall().stroke(editor, cursor);
  }

  private void spire(Coord origin) {
    Coord cursor;
    Coord start;
    Coord end;
    for (Direction dir : Direction.CARDINAL) {

      // outer wall
      start = origin.copy();
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      end.up(2);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      // doors
      cursor = origin.copy();
      cursor.translate(dir, 3);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.up();
      SingleBlockBrush.AIR.stroke(editor, cursor);

      // wall cap
      start = origin.copy();
      start.translate(dir, 2);
      start.up(3);
      end = start.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      end.translate(dir);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(dir);
      start.up(4);
      end = start.copy();
      end.translate(dir, 2);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      // corner spikes
      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.up(3);
      end = start.copy();
      end.up();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      start.up(3);
      end = start.copy();
      end.up(4);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(dir);
      start.translate(dir.antiClockwise());
      start.up(4);
      end = start.copy();
      end.up(3);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(dir);
      start.up(7);
      end = start.copy();
      end.up(2);
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
    }

    start = origin.copy();
    start.up(7);
    end = start.copy();
    end.up(6);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.up(7);
    getPrimaryLight().stroke(editor, cursor);
  }

  private void wall(Direction dir, Coord pos) {
    // upper wall lip
    Coord start = pos.copy();
    start.up(4);
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    // inner wall
    start = pos.copy();
    start.translate(dir.reverse());
    end = start.copy();
    end.translate(dir.reverse());
    end.up(2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

    Coord cursor = pos.copy();
    cursor.translate(dir.reverse(), 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      Coord c2 = pos.copy();
      for (int i = 0; i < 5; ++i) {
        if (i % 2 == 0) {
          cursor = c2.copy();
          cursor.up(5);
          getPrimaryWall().stroke(editor, cursor);

          start = c2.copy();
          start.up();
          end = start.copy();
          end.up(2);
          SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
        } else {
          cursor = c2.copy();
          cursor.translate(dir);
          getPrimaryWall().stroke(editor, cursor);
          cursor.up();
          getPrimaryWall().stroke(editor, cursor);
        }
        c2.translate(o);
      }

      cursor = pos.copy();
      cursor.translate(dir.reverse(), 2);
      cursor.translate(o, 2);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.up();
      SingleBlockBrush.AIR.stroke(editor, cursor);
    }
  }

  private void corner(Direction dir, Coord pos) {

    Direction[] faces = {dir, dir.antiClockwise()};

    Coord start;
    Coord end;
    for (Direction face : faces) {
      start = pos.copy();
      start.translate(face);
      end = start.copy();
      end.translate(face.antiClockwise());
      start.translate(face.clockwise());
      end.up();
      getPrimaryWall().fill(editor, RectSolid.newRect(start, end));

      Coord cursor = pos.copy();
      cursor.translate(face, 2);
      getPrimaryWall().stroke(editor, cursor);
      cursor.up();
      getPrimaryWall().stroke(editor, cursor);

      cursor = pos.copy();
      cursor.translate(face);
      cursor.up(2);
      getPrimaryWall().stroke(editor, cursor);
      cursor.up();
      getPrimaryWall().stroke(editor, cursor);
    }

    start = pos.copy();
    start.up(4);
    end = start.copy();
    end.up(2);
    getPrimaryWall().fill(editor, RectSolid.newRect(start, end));
  }


}
