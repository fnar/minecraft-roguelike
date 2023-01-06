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
    this.wallDist = 5;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    Coord start = at.copy();
    Coord end = at.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    Coord cursor = at.copy();
    cursor.up(5);
    primaryLightBrush().stroke(worldEditor, cursor);

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    primaryFloorBrush().fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction dir : Direction.CARDINAL) {

      start = at.copy();
      end = at.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.up(4);
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

      start = at.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      start.up(4);
      end = start.copy();
      end.translate(dir.clockwise(), 4);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction o : dir.orthogonals()) {
        cursor = at.copy();
        cursor.translate(dir, 3);
        cursor.up(2);
        cursor.translate(o, 2);
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        primaryWallBrush().stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }
    }

    generateDoorways(at, entrances);

    return this;
  }

}
