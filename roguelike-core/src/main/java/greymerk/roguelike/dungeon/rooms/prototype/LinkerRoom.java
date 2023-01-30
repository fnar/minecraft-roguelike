package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class LinkerRoom extends BaseRoom {

  public LinkerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 4;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Coord start = at.copy();
    Coord end = at.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 9, 4));
    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-4, 9, -4));
    end.translate(new Coord(4, 9, 4));
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    primaryFloorBrush().fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction dir : Direction.CARDINAL) {

      start = at.copy();
      start.translate(dir, 4);
      end = start.copy();
      end.up(8);
      start.down();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(worldEditor, BlockType.IRON_BAR.getBrush(), true, false);

      start = at.copy();
      end = at.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.up(8);
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));
    }


    return this;
  }

}
