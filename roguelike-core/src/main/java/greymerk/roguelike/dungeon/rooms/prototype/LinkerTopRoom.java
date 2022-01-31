package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class LinkerTopRoom extends BaseRoom {

  public LinkerTopRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    Coord cursor = origin.copy();
    cursor.up(5);
    lights().stroke(worldEditor, cursor);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(worldEditor, floors());

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      end = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.up(4);
      RectSolid.newRect(start, end).fill(worldEditor, pillars());

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      start.up(4);
      end = start.copy();
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(worldEditor, walls());
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()));

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 3);
        cursor.up(2);
        cursor.translate(o, 2);
        stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        walls().stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }
    }

    generateDoorways(origin, entrances);

    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
