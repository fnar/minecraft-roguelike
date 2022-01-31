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
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    RectSolid.newRect(
        new Coord(x - 7, y, z - 7),
        new Coord(x + 7, y + 5, z + 7)
    ).fill(worldEditor, SingleBlockBrush.AIR);
    RectHollow.newRect(new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8)).fill(worldEditor, walls(), false, true);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 5, z + 1)).fill(worldEditor, walls());

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {
      Coord start;
      Coord end;
      for (Direction orthogonal : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 2);
        start = cursor.copy();
        end = start.copy();
        end.up(5);
        RectSolid.newRect(start, end).fill(worldEditor, walls());
        cursor.translate(dir.reverse());
        stairs().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        start = cursor.copy();
        end = start.copy();
        end.up(2);
        RectSolid.newRect(start, end).fill(worldEditor, walls());
        cursor.translate(dir.reverse());
        stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        start = cursor.copy();
        end = start.copy();
        end.up();
        RectSolid.newRect(start, end).fill(worldEditor, walls());
        cursor.up();
        cursor.translate(dir.reverse());
        stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = origin.copy();
        start.translate(dir, 7);
        start.up(3);
        end = start.copy();
        end.up(2);
        end.translate(orthogonal);
        RectSolid.newRect(start, end).fill(worldEditor, walls());
        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls());
        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls());

        cursor = origin.copy();
        cursor.translate(dir, 8);
        cursor.up(2);
        cursor.translate(orthogonal);
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor, true, false);
        cursor.translate(dir.reverse());
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse(), 2);
        stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

        start = origin.copy();
        start.translate(dir, 7);
        start.translate(orthogonal, 3);
        start.up(3);
        end = start.copy();
        end.up(2);
        end.translate(orthogonal, 2);
        theme().getPrimary().getPillar().fill(worldEditor, new RectSolid(start, end));

        start.translate(dir.reverse());
        start.up();
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, theme().getPrimary().getPillar());

        cursor = origin.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 3);
        stairs().setUpsideDown(false).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        stairs().setUpsideDown(false).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(orthogonal.reverse(), 2);
        stairs().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        stairs().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);
        cursor.translate(orthogonal, 2);
        stairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        cursor.up();
        end = cursor.copy();
        end.translate(orthogonal.reverse(), 2);
        RectSolid.newRect(cursor, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()));
        cursor.up();
        end.up();
        RectSolid.newRect(cursor, end).fill(worldEditor, walls());
        end.translate(dir.reverse());
        stairs().setUpsideDown(true).setFacing(orthogonal).stroke(worldEditor, cursor);

        cursor = origin.copy();
        cursor.translate(dir, 7);
        cursor.translate(orthogonal, 4);
        cursor.down();
        lights().stroke(worldEditor, cursor);

      }

      Direction o = dir.antiClockwise();

      start = origin.copy();
      start.translate(dir, 6);
      start.translate(o, 6);
      end = start.copy();
      end.translate(dir);
      end.translate(o);
      end.up(5);
      RectSolid.newRect(start, end).fill(worldEditor, theme().getPrimary().getPillar());

      cursor = origin.copy();
      walls().stroke(worldEditor, cursor);
      cursor.translate(dir);
      stairs().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o);
      stairs().setUpsideDown(false).setFacing(dir).stroke(worldEditor, cursor);
      cursor.up(4);
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    }

    cursor = origin.copy();
    cursor.up(4);
    lights().stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.up();
    Direction chestFacing = getEntrance(entrances).reverse();
    generateChest(cursor, chestFacing, getChestTypeOrUse(ChestType.REWARD));

    generateDoorways(origin, entrances);

    return this;
  }

  @Override
  public int getSize() {
    return 11;
  }
}
