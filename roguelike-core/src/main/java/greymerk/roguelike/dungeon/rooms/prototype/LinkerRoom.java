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
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 9, 4));
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, 9, -4));
    end.translate(new Coord(4, 9, 4));
    RectSolid.newRect(start, end).fill(worldEditor, walls());

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(worldEditor, floors());

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      start.translate(dir, 4);
      end = start.copy();
      end.up(8);
      start.down();
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(worldEditor, BlockType.IRON_BAR.getBrush(), true, false);

      start = origin.copy();
      end = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.up(8);
      RectSolid.newRect(start, end).fill(worldEditor, pillars());
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
