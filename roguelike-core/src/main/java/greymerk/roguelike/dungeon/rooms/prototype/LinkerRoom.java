package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class LinkerRoom extends BaseRoom {

  public LinkerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Theme theme = levelSettings.getTheme();

    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    BlockBrush bars = BlockType.IRON_BAR.getBrush();

    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 9, 4));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, 9, -4));
    end.translate(new Coord(4, 9, 4));
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      start.translate(dir, 4);
      end = start.copy();
      end.up(8);
      start.down();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(worldEditor, bars, true, false);

      start = origin.copy();
      end = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.up(8);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
