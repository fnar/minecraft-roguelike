package greymerk.roguelike.dungeon.rooms.prototype;

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

public class DungeonLinkerTop extends DungeonBase {

  public DungeonLinkerTop(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = levelSettings.getTheme();

    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    cursor = origin.copy();
    cursor.translate(Cardinal.UP, 5);
    levelSettings.getTheme().getPrimary().getLightBlock().stroke(worldEditor, cursor);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = origin.copy();
      end = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.translate(Cardinal.UP, 4);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      start.translate(Cardinal.UP, 4);
      end = start.copy();
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(worldEditor, wall);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, stair.setUpsideDown(true).setFacing(dir.reverse()));

      for (Cardinal o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 3);
        cursor.translate(Cardinal.UP, 2);
        cursor.translate(o, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP);
        wall.stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
