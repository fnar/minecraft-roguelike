package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Skull;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class OssuaryRoom extends BaseRoom {

  public OssuaryRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 9;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    Coord start;
    Coord end;
    Coord cursor;

    start = at.copy();
    end = at.copy();
    start.down();
    start.north(8);
    start.east(8);
    end.south(8);
    end.west(8);
    end.up(6);
    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    // entrance arches
    for (Direction dir : Direction.CARDINAL) {
      cursor = at.copy();
      cursor.translate(dir, 7);
      for (Direction o : dir.orthogonals()) {
        start = cursor.copy();
        start.translate(o, 2);
        end = start.copy();
        end.up(5);
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

        start = cursor.copy();
        start.translate(o, 2);
        start.up(2);
        start.translate(dir.reverse(), 2);
        end = start.copy();
        end.up(3);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start = cursor.copy();
        start.up(3);
        end = start.copy();
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.up(3);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start.up();
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start.up();
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

        Coord c = cursor.copy();
        c.translate(o);
        c.up(2);
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
        c.up();
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
        c.up();
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
      }

      Coord c = at.copy();
      c.translate(dir, 7);
      c.up(3);
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
      c.translate(dir.reverse());
      c.up();
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = at.copy();
      cursor.translate(dir, 4);
      cursor.up(5);
      start = cursor.copy();
      start.north();
      start.east();
      end = cursor.copy();
      end.south();
      end.west();
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Direction d : Direction.CARDINAL) {
        Coord c = cursor.copy();
        c.translate(d);
        primaryStairBrush().setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
      }
    }

    // corner pillars
    for (Direction dir : Direction.CARDINAL) {
      start = at.copy();
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = start.copy();
      end.translate(dir);
      end.translate(dir.antiClockwise());
      end.up(6);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
    }

    // central ceiling
    cursor = at.copy();
    cursor.up(6);
    start = cursor.copy();
    start.north(2);
    start.east(2);
    end = cursor.copy();
    end.south(2);
    end.west(2);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
    start.down();
    end.down();
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    for (Direction d : Direction.CARDINAL) {
      Coord c = cursor.copy();
      c.translate(d);
      primaryStairBrush().setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
    }

    for (Direction dir : Direction.CARDINAL) {
      Direction[] orthogonal = dir.orthogonals();
      cursor = at.copy();
      cursor.up(5);
      cursor.translate(dir, 2);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Direction o : orthogonal) {
        Coord c = cursor.copy();
        c.translate(o);
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
      }
      cursor.translate(orthogonal[0], 2);
      primaryWallBrush().stroke(worldEditor, cursor);
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = at.copy();
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      cursor.up(5);
      start = cursor.copy();
      start.north();
      start.east();
      end = cursor.copy();
      end.south();
      end.west();
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Direction d : Direction.CARDINAL) {
        Coord c = cursor.copy();
        c.translate(d);
        primaryStairBrush().setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
      }

      for (Direction d : new Direction[]{dir, dir.antiClockwise()}) {
        cursor = at.copy();
        cursor.translate(dir, 4);
        cursor.translate(dir.antiClockwise(), 4);
        cursor.up(4);
        cursor.translate(d, 2);
        SingleBlockBrush.AIR.stroke(worldEditor, cursor);
        for (Direction o : d.orthogonals()) {
          Coord c = cursor.copy();
          c.translate(o);
          primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
        }

        start = at.copy();
        start.translate(dir, 4);
        start.translate(dir.antiClockwise(), 4);
        start.translate(d, 3);
        end = start.copy();
        end.up(4);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start = end.copy();
        start.translate(d.orthogonals()[0]);
        end.translate(d.orthogonals()[1]);
        end.up(2);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start.translate(d.reverse());
        end.translate(d.reverse());
        start.up();
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

        for (Direction o : d.orthogonals()) {
          cursor = at.copy();
          cursor.translate(dir, 4);
          cursor.translate(dir.antiClockwise(), 4);
          cursor.translate(d, 3);
          cursor.translate(o);
          primaryWallBrush().stroke(worldEditor, cursor);
          cursor.up();
          skull(worldEditor, d.reverse(), cursor);
          cursor.up();
          primaryWallBrush().stroke(worldEditor, cursor);
          cursor.up();
          skull(worldEditor, d.reverse(), cursor);
          cursor.up();
          cursor.translate(d.reverse());
          primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        }
      }

      cursor.translate(dir, 2);
    }

    return this;
  }

  private void skull(WorldEditor editor, Direction dir, Coord origin) {
    if (editor.getRandom().nextInt(3) == 0) {
      return;
    }

    Coord cursor = origin.copy();
    cursor.down();
    if (editor.isAirBlock(cursor)) {
      return;
    }

    Skull skull = editor.getRandom().nextInt(15) == 0 ? Skull.WITHER : Skull.SKELETON;
    editor.setSkull(editor, origin, dir, skull);
  }

}
