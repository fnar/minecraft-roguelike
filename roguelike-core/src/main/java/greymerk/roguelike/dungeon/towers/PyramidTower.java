package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PyramidTower extends Tower {

  @Override
  public void generate(WorldEditor editor, Random rand, Theme theme, Coord dungeon) {

    Coord floor = TowerType.getBaseCoord(editor, dungeon);
    floor.up();
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

    for (Direction dir : Direction.CARDINAL) {
      cursor = floor.copy();
      cursor.translate(dir, 6);
      wall(editor, theme, dir, cursor);
      cursor.translate(dir.antiClockwise(), 6);
      corner(editor, theme, dir, cursor);
    }

    // todo: Should the Entrance always be to the East?
    cursor = floor.copy();
    cursor.east(6);
    entrance(editor, theme, Direction.EAST, cursor);

    cursor = floor.copy();
    cursor.up(4);
    spire(editor, theme, cursor);

    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

  }

  private void entrance(WorldEditor editor, Theme theme, Direction dir, Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.up(3);
    end = start.copy();
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, blocks);

    for (Direction o : dir.orthogonals()) {
      start = origin.copy();
      start.translate(dir);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir.reverse());
      end.up(3);
      RectSolid.newRect(start, end).fill(editor, blocks);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(o, 2);
      blocks.stroke(editor, cursor);
      cursor.up();
      blocks.stroke(editor, cursor);
    }

    // door
    start = origin.copy();
    end = start.copy();
    start.translate(dir.reverse());
    end.translate(dir);
    end.up();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.translate(dir);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = origin.copy();
    cursor.up(2);
    blocks.stroke(editor, cursor);

    // door cap
    start = origin.copy();
    start.up(3);
    start.translate(dir);
    end = start.copy();
    end.up(2);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = origin.copy();
    cursor.translate(dir);
    cursor.up(4);
    BlockType.LAPIS_BLOCK.getBrush().stroke(editor, cursor);

    cursor.up(2);
    blocks.stroke(editor, cursor);
    cursor.up();
    blocks.stroke(editor, cursor);
  }

  private void spire(WorldEditor editor, Theme theme, Coord origin) {
    BlockBrush blocks = theme.getPrimary().getWall();
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
      RectSolid.newRect(start, end).fill(editor, blocks);

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
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = origin.copy();
      start.translate(dir);
      start.up(4);
      end = start.copy();
      end.translate(dir, 2);
      RectSolid.newRect(start, end).fill(editor, blocks);

      // corner spikes
      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.up(3);
      end = start.copy();
      end.up();
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = origin.copy();
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      start.up(3);
      end = start.copy();
      end.up(4);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = origin.copy();
      start.translate(dir);
      start.translate(dir.antiClockwise());
      start.up(4);
      end = start.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(editor, blocks);

      start = origin.copy();
      start.translate(dir);
      start.up(7);
      end = start.copy();
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, blocks);
    }

    start = origin.copy();
    start.up(7);
    end = start.copy();
    end.up(6);
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = origin.copy();
    cursor.up(7);
    theme.getPrimary().getLightBlock().stroke(editor, cursor);

  }

  private void wall(WorldEditor editor, Theme theme, Direction dir, Coord pos) {
    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    // upper wall lip
    start = pos.copy();
    start.up(4);
    end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(editor, blocks);

    // inner wall
    start = pos.copy();
    start.translate(dir.reverse());
    end = start.copy();
    end.translate(dir.reverse());
    end.up(2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, blocks);

    cursor = pos.copy();
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
          blocks.stroke(editor, cursor);

          start = c2.copy();
          start.up();
          end = start.copy();
          end.up(2);
          RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
        } else {
          cursor = c2.copy();
          cursor.translate(dir);
          blocks.stroke(editor, cursor);
          cursor.up();
          blocks.stroke(editor, cursor);
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

  private void corner(WorldEditor editor, Theme theme, Direction dir, Coord pos) {

    BlockBrush blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    Direction[] faces = {dir, dir.antiClockwise()};

    for (Direction face : faces) {
      start = pos.copy();
      start.translate(face);
      end = start.copy();
      end.translate(face.antiClockwise());
      start.translate(face.clockwise());
      end.up();
      RectSolid.newRect(start, end).fill(editor, blocks);

      cursor = pos.copy();
      cursor.translate(face, 2);
      blocks.stroke(editor, cursor);
      cursor.up();
      blocks.stroke(editor, cursor);

      cursor = pos.copy();
      cursor.translate(face);
      cursor.up(2);
      blocks.stroke(editor, cursor);
      cursor.up();
      blocks.stroke(editor, cursor);
    }

    start = pos.copy();
    start.up(4);
    end = start.copy();
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, blocks);
  }


}
