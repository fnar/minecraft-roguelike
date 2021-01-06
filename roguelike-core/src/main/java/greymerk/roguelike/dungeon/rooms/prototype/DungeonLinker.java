package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;

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

public class DungeonLinker extends DungeonBase {

  public DungeonLinker(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 9, 4));
    RectHollow.newRect(start, end).fill(editor, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 9, -4));
    end.translate(new Coord(4, 9, 4));
    RectSolid.newRect(start, end).fill(editor, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(editor, floor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(dir, 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 8);
      start.translate(Cardinal.DOWN);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(editor, bars, true, false);

      start = new Coord(origin);
      end = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.translate(Cardinal.UP, 8);
      RectSolid.newRect(start, end).fill(editor, pillar);
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
