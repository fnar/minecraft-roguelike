package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonDarkHall extends DungeonBase {

  public DungeonDarkHall(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    BlockBrush outerWall = theme.getPrimary().getWall();
    BlockBrush wall = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 7);
    start.translate(Cardinal.WEST, 7);
    end.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.EAST, 7);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 7);

    RectHollow.fill(editor, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    start.translate(Cardinal.UP, 6);
    end.translate(Cardinal.UP, 9);

    RectHollow.fill(editor, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);

    RectSolid.fill(editor, start, end, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : entrances) {
      Cardinal[] orthogonal = dir.orthogonals();
      start = new Coord(origin);
      start.translate(orthogonal[0]);
      end = new Coord(origin);
      end.translate(orthogonal[1]);
      end.translate(dir, 7);
      RectSolid.fill(editor, start, end, theme.getSecondary().getFloor(), false, true);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = new Coord(start);
      end.translate(Cardinal.UP, 5);
      RectSolid.fill(editor, start, end, pillar);

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.fill(editor, start, end, wall);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.fill(editor, start, end, wall);
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, start, end, wall);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 7);
      pillar.stroke(editor, start);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      end.translate(dir.reverse(), 3);
      RectSolid.fill(editor, start, end, wall);

      if (entrances.contains(dir)) {
        start = new Coord(origin);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir.clockwise(), 2);
        RectSolid.fill(editor, start, end, wall);

        cursor = new Coord(origin);
        cursor.translate(dir, 7);
        cursor.translate(Cardinal.UP, 2);
        SingleBlockBrush.AIR.stroke(editor, cursor);

        for (Cardinal o : dir.orthogonals()) {
          cursor = new Coord(origin);
          cursor.translate(dir, 7);
          cursor.translate(Cardinal.UP, 2);
          cursor.translate(o);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);

          cursor = new Coord(origin);
          cursor.translate(dir, 6);
          cursor.translate(o, 3);
          pillar(editor, settings, o.reverse(), cursor);

          cursor = new Coord(origin);
          cursor.translate(dir, 7);
          cursor.translate(o, 2);
          pillar.stroke(editor, cursor);
          cursor.translate(Cardinal.UP);
          pillar.stroke(editor, cursor);
        }
      } else {
        cursor = new Coord(origin);
        cursor.translate(dir, 6);
        pillar(editor, settings, dir.reverse(), cursor);
      }

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      end.translate(dir.reverse(), 2);
      RectSolid.fill(editor, start, end, wall);

      for (Cardinal o : dir.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 6);
        cursor.translate(o, 3);
        pillar(editor, settings, dir.reverse(), cursor);
        start = new Coord(cursor);
        start.translate(Cardinal.UP, 6);
        end = new Coord(start);
        end.translate(dir.reverse(), 6);
        RectSolid.fill(editor, start, end, wall);
      }
    }

    return this;
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {

    ThemeBase theme = settings.getTheme();

    BlockBrush wall = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(Cardinal.UP, 5);
    RectSolid.fill(editor, start, end, pillar);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    if (editor.isAirBlock(cursor)) {
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    } else {
      wall.stroke(editor, cursor);
    }

  }

  @Override
  public int getSize() {
    return 9;
  }


}
