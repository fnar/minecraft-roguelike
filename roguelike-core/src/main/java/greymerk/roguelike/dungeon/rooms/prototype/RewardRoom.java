package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class RewardRoom extends BaseRoom {

  public RewardRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    int x = at.getX();
    int y = at.getY();
    int z = at.getZ();

    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(
        new Coord(x - 7, y, z - 7),
        new Coord(x + 7, y + 5, z + 7)
    ));
    RectHollow.newRect(new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8)).fill(worldEditor, primaryWallBrush(), false, true);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 5, z + 1)));

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      Coord start;
      Coord end;
      for (Direction orthogonal : dir.orthogonals()) {
        cursor = at.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 2);
        start = cursor.copy();
        end = start.copy();
        end.up(5);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        cursor.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        start = cursor.copy();
        end = start.copy();
        end.up(2);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        cursor.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        start = cursor.copy();
        end = start.copy();
        end.up();
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        cursor.up();
        cursor.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = at.copy();
        start.translate(dir, 7);
        start.up(3);
        end = start.copy();
        end.up(2);
        end.translate(orthogonal);
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

        cursor = at.copy();
        cursor.translate(dir, 8);
        cursor.up(2);
        cursor.translate(orthogonal);
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor, true, false);
        cursor.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse(), 2);
        primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

        start = at.copy();
        start.translate(dir, 7);
        start.translate(orthogonal, 3);
        start.up(3);
        end = start.copy();
        end.up(2);
        end.translate(orthogonal, 2);
        theme().getPrimary().getPillar().fill(worldEditor, RectSolid.newRect(start, end));

        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        theme().getPrimary().getPillar().fill(worldEditor, RectSolid.newRect(start, end));

        cursor = at.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 3);
        primaryStairBrush().setUpsideDown(false).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        primaryStairBrush().setUpsideDown(false).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(orthogonal.reverse(), 2);
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        end = cursor.copy();
        end.translate(orthogonal.reverse(), 2);
        primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).fill(worldEditor, RectSolid.newRect(cursor, end));
        cursor.up();
        end.up();
        primaryWallBrush().fill(worldEditor, RectSolid.newRect(cursor, end));
        end.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);

        cursor = at.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 4);
        cursor.down();
        primaryLightBrush().stroke(worldEditor, cursor);

      }

      Direction o = dir.antiClockwise();

      start = at.copy();
      start.translate(dir, 6);
      start.translate(o, 6);
      end = start.copy();
      end.translate(dir);
      end.translate(o);
      end.up(5);
      theme().getPrimary().getPillar().fill(worldEditor, RectSolid.newRect(start, end));

      cursor = at.copy();
      primaryWallBrush().stroke(worldEditor, cursor);
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o);
      primaryStairBrush().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.up(4);
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    }

    cursor = at.copy();
    cursor.up(4);
    primaryLightBrush().stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.up();
    Direction chestFacing = getEntrance(entrances).reverse();
    generateChest(cursor, chestFacing, getChestTypeOrUse(ChestType.REWARD));

    generateDoorways(at, entrances);

    return this;
  }

  @Override
  public int getSize() {
    return 11;
  }
}
