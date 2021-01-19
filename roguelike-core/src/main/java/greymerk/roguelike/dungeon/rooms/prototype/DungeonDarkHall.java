package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonDarkHall extends DungeonBase {

  public DungeonDarkHall(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    ThemeBase theme = levelSettings.getTheme();

    BlockBrush outerWall = theme.getPrimary().getWall();
    BlockBrush wall = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();

    start.north(7);
    start.west(7);
    end.south(7);
    end.east(7);
    start.down();
    end.up(7);

    RectHollow.newRect(start, end).fill(worldEditor, outerWall, false, true);

    start = origin.copy();
    end = origin.copy();

    start.north(4);
    start.west(4);
    end.south(4);
    end.east(4);
    start.up(6);
    end.up(9);

    RectHollow.newRect(start, end).fill(worldEditor, outerWall, false, true);

    start = origin.copy();
    end = origin.copy();

    start.north(6);
    start.west(6);
    end.south(6);
    end.east(6);
    start.down();
    end.down();

    RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getFloor(), false, true);

    for (Direction dir : entrances) {
      Direction[] orthogonal = dir.orthogonals();
      start = origin.copy();
      start.translate(orthogonal[0]);
      end = origin.copy();
      end.translate(orthogonal[1]);
      end.translate(dir, 7);
      RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getFloor(), false, true);
    }

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = start.copy();
      end.up(5);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      start = origin.copy();
      start.translate(dir, 6);
      start.up(6);
      end = start.copy();
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = origin.copy();
      start.translate(dir, 3);
      start.up(6);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);
      start.up(2);
      end.up(2);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = origin.copy();
      start.translate(dir, 3);
      start.up(7);
      pillar.stroke(worldEditor, start);
      start.up();
      end = start.copy();
      end.translate(dir.reverse(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      if (entrances.contains(dir)) {
        start = origin.copy();
        start.translate(dir, 7);
        start.up(2);
        end = start.copy();
        end.up(3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir.clockwise(), 2);
        RectSolid.newRect(start, end).fill(worldEditor, wall);

        cursor = origin.copy();
        cursor.translate(dir, 7);
        cursor.up(2);
        SingleBlockBrush.AIR.stroke(worldEditor, cursor);

        for (Direction o : dir.orthogonals()) {
          cursor = origin.copy();
          cursor.translate(dir, 7);
          cursor.up(2);
          cursor.translate(o);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);

          cursor = origin.copy();
          cursor.translate(dir, 6);
          cursor.translate(o, 3);
          pillar(worldEditor, levelSettings, o.reverse(), cursor);

          cursor = origin.copy();
          cursor.translate(dir, 7);
          cursor.translate(o, 2);
          pillar.stroke(worldEditor, cursor);
          cursor.up();
          pillar.stroke(worldEditor, cursor);
        }
      } else {
        cursor = origin.copy();
        cursor.translate(dir, 6);
        pillar(worldEditor, levelSettings, dir.reverse(), cursor);
      }

      start = origin.copy();
      start.translate(dir, 6);
      start.up(6);
      end = start.copy();
      end.translate(dir.reverse(), 2);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 6);
        cursor.translate(o, 3);
        pillar(worldEditor, levelSettings, dir.reverse(), cursor);
        start = cursor.copy();
        start.up(6);
        end = start.copy();
        end.translate(dir.reverse(), 6);
        RectSolid.newRect(start, end).fill(worldEditor, wall);
      }
    }

    return this;
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {

    ThemeBase theme = settings.getTheme();

    BlockBrush wall = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = start.copy();
    end.up(5);
    RectSolid.newRect(start, end).fill(editor, pillar);

    cursor = origin.copy();
    cursor.up(3);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.up();
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.up();
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
