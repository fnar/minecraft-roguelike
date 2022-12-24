package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.carpet;

public class DungeonsMusic extends BaseRoom {

  public DungeonsMusic(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-6, -1, -6));
    end.translate(new Coord(6, 5, 6));
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-6, 4, -6));
    end.translate(new Coord(6, 5, 6));
    secondaryWalls().fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 4, -3));
    end.translate(new Coord(3, 4, 3));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    secondaryFloors().fill(worldEditor, RectSolid.newRect(start, end));

    for (int i = 2; i >= 0; --i) {
      start = origin.copy();
      end = origin.copy();
      start.translate(new Coord(-i - 1, 0, -i - 1));
      end.translate(new Coord(i + 1, 0, i + 1));
      BlockBrush carpet = carpet().setColor(DyeColor.chooseRandom(random()));
      carpet.fill(worldEditor, RectSolid.newRect(start, end));
    }

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {

      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.up(3);
      secondaryWalls().stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      pillar(cursor);

      start = origin.copy();
      start.up(4);
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      secondaryPillars().fill(worldEditor, RectSolid.newRect(start, end));

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      secondaryStairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 2);
        pillar(cursor);

        cursor = origin.copy();
        cursor.translate(dir, 4);
        cursor.up(3);
        cursor.translate(o);
        secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o);
        secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        secondaryStairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        secondaryStairs().setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
        cursor.up();
        secondaryWalls().stroke(worldEditor, cursor);
        cursor.translate(o);
        secondaryWalls().stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        secondaryStairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      }
    }

    BlockType.JUKEBOX.getBrush().stroke(worldEditor, origin);

    cursor = origin.copy();
    cursor.up(4);
    lights().stroke(worldEditor, cursor);

    generateChest(generateChestLocation(origin.copy().up()), getEntrance(entrances).reverse(), ChestType.MUSIC);

    generateDoorways(origin, entrances);

    return this;
  }

  @Override
  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 5)
        .translate(dir1, 3 + (random().nextBoolean() ? 1 : 0));
  }

  private void pillar(Coord origin) {
    Coord start = origin.copy();
    Coord end = start.copy();
    end.up(2);
    secondaryPillars().fill(worldEditor, RectSolid.newRect(start, end));
    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = end.copy();
      cursor.translate(dir);
      secondaryStairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor, true, false);
      cursor.up();
      secondaryWalls().stroke(worldEditor, cursor);
    }
  }

  public int getSize() {
    return 8;
  }

}
