package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.spawner.MobType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsCrypt extends BaseRoom {

  public DungeonsCrypt(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Coord start = at.copy();
    Coord end = at.copy();
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 4, 3));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-9, -1, -9));
    end.translate(new Coord(9, -1, 9));
    primaryFloorBrush().fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-9, 5, -9));
    end.translate(new Coord(9, 6, 9));
    RectSolid.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    for (Direction dir : Direction.CARDINAL) {

      if (entrances.contains(dir) && entrances.contains(dir.antiClockwise())) {
        start = at.copy();
        end = at.copy();
        start.translate(dir, 3);
        end.translate(dir.antiClockwise(), 5);
        end.translate(dir, 5);
        end.up(4);
        SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));
      }

      Coord cursor;
      if (entrances.contains(dir)) {
        // doorway air
        start = at.copy();
        end = at.copy();
        start.translate(dir, 3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir, 8);
        end.translate(dir.clockwise(), 2);
        end.up(4);
        SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

        for (Direction o : dir.orthogonals()) {
          if (entrances.contains(o)) {

            cursor = at.copy();
            cursor.translate(dir, 7);
            cursor.translate(o, 3);
            cursor.up();

            crypt(cursor, o);
          } else {

            start = at.copy();
            end = at.copy();
            start.translate(dir, 4);
            start.translate(o, 3);
            end.translate(dir, 8);
            end.translate(o, 8);
            end.up(4);
            SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

            cursor = at.copy();
            cursor.translate(dir, 6);
            cursor.translate(o, 3);
            cursor.up();

            sarcophagus(cursor, o);
          }
        }

      } else {
        cursor = at.copy();
        cursor.translate(dir, 4);
        mausoleumWall(cursor, dir);
      }

      cursor = at.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(cursor);

      start = at.copy();
      start.translate(dir, 8);
      start.up(4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(worldEditor, primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()), true, false);
    }

    return this;
  }

  private void sarcophagus(Coord origin, Direction dir) {
    Coord start = origin.copy();
    start.down();
    start.translate(dir, 5);
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor = origin.copy();
    cursor.translate(dir, 5);
    cursor.up(3);
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      start = origin.copy();
      start.down();
      start.translate(dir);
      start.translate(o, 3);
      end = start.copy();
      end.translate(dir, 4);
      end.up(4);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

      cursor = origin.copy();
      cursor.down();
      cursor.translate(dir, 5);
      cursor.translate(o, 2);
      pillar(cursor);

      start = origin.copy();
      start.up(3);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir, 3);
      primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
    }

    cursor = origin.copy();
    tomb(cursor, dir);

    cursor.up();
    primaryStairBrush().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.down(2);
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.translate(dir);
    primaryWallBrush().stroke(worldEditor, cursor);
    cursor.translate(dir);
    primaryWallBrush().stroke(worldEditor, cursor);
    cursor.translate(dir);
    primaryStairBrush().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
    cursor.up();
    primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    cursor.up();
    primaryStairBrush().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.down();
      cursor.translate(o);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 3);
      primaryStairBrush().setUpsideDown(false).setFacing(o).fill(worldEditor, RectSolid.newRect(start, end));
      start.up();
      end.up();
      primaryStairBrush().setUpsideDown(true).setFacing(o).fill(worldEditor, RectSolid.newRect(start, end));
      start.up();
      end.up();
      primaryStairBrush().setUpsideDown(false).setFacing(o).fill(worldEditor, RectSolid.newRect(start, end));
    }

  }

  private void crypt(Coord origin, Direction dir) {
    Coord start = origin.copy();
    start.down();
    start.translate(dir.antiClockwise());
    Coord end = origin.copy();
    end.up(3);
    end.translate(dir.clockwise());
    end.translate(dir, 3);

    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor = origin.copy();
    cursor.translate(dir.reverse());
    cursor.up(2);
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.up();
    primaryWallBrush().stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir.reverse());
      cursor.up();
      cursor.translate(o);
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      primaryWallBrush().stroke(worldEditor, cursor);
      cursor.up();
      primaryWallBrush().stroke(worldEditor, cursor);

      start = origin.copy();
      start.up(3);
      start.translate(dir.reverse(), 2);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir, 7);
      RectSolid.newRect(start, end).fill(worldEditor, primaryStairBrush().setUpsideDown(true).setFacing(o), true, false);
    }

    start = origin.copy();
    start.up(3);
    start.translate(dir.reverse(), 2);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

    tomb(origin, dir);
  }

  private void mausoleumWall(Coord origin, Direction dir) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    end.translate(dir, 4);
    end.up(4);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor = origin.copy();
    cursor.up();
    tomb(cursor, dir);

    cursor.up(2);
    tomb(cursor, dir);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.up();
      cursor.translate(o, 2);
      tomb(cursor, dir);

      cursor.up(2);
      tomb(cursor, dir);
    }

  }

  private void pillar(Coord origin) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    end.up(4);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = end.copy();
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor, true, false);
    }
  }

  private void tomb(Coord origin, Direction dir) {

    BlockBrush tombStone = BlockType.QUARTZ.getBrush();

    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    cursor.up();
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

    cursor.translate(dir.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(origin, cursor));

    if (random().nextInt(4) == 0) {
      return;
    }

    cursor = origin.copy();
    tombStone.stroke(worldEditor, cursor);

    if (random().nextInt(5) != 0) {
      return;
    }

    cursor.translate(dir);
    generateSpawner(cursor, MobType.UNDEAD_MOBS);

    cursor.translate(dir);
    generateChest(cursor, dir);
  }

  public int getSize() {
    return 10;
  }
}
