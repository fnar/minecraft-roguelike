package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

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

public class DungeonsFire extends BaseRoom {


  public DungeonsFire(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public static void genFire(WorldEditor editor, Theme theme, Coord origin) {

    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    cursor = origin.copy();
    BlockType.NETHERRACK.getBrush().stroke(editor, cursor);
    cursor.up();
    BlockType.FIRE.getBrush().stroke(editor, cursor);

    for (Direction dir : Direction.CARDINAL) {

      start = origin.copy();
      start.translate(dir);
      start.translate(dir.antiClockwise());
      end = start.copy();
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, pillar, true, false);

      cursor = origin.copy();
      cursor.translate(dir);
      stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor, true, false);
      cursor.up();
      BlockType.IRON_BAR.getBrush().stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);

      cursor = origin.copy();
      cursor.up(6);
      cursor.translate(dir, 3);

      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 2);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, c, true, false);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, c, true, false);
      }

      cursor = origin.copy();
      cursor.up();
      cursor.translate(dir, 2);

      if (!editor.isAirBlock(cursor)) {
        continue;
      }

      start = origin.copy();
      start.up(3);
      start.translate(dir, 2);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir);
      RectSolid.newRect(start, end).fill(editor, stair, true, false);
    }

    start = origin.copy();
    start.up(3);
    start.north(2);
    start.west(2);
    end = origin.copy();
    end.up(7);
    end.south(2);
    end.east(2);

    RectSolid.newRect(start, end).fill(editor, wall, true, false);

  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Coord start = origin.copy().north(8).west(8).down();
    Coord end = origin.copy().south(8).east(8).up(7);

    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy().down();
    end = start.copy();
    start.north(8).west(8);
    end.south(8).east(8);
    RectSolid.newRect(start, end).fill(worldEditor, theme().getPrimary().getFloor(), false, true);

    for (Direction dir : Direction.CARDINAL) {
      Coord cursor;
      for (Direction orth : dir.orthogonals()) {
        start = origin.copy().translate(dir, 7).translate(orth, 2);
        end = start.copy().up(6);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor = origin.copy().translate(dir, 8).translate(orth).up(2);
        stairs().setUpsideDown(true).setFacing(orth.reverse()).stroke(worldEditor, cursor, true, false);

        cursor.translate(dir.reverse()).up();
        stairs().setUpsideDown(true).setFacing(orth.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy().up();
        end = start.copy().up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor.translate(dir.reverse()).translate(orth);
        stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy().up();
        end = start.copy().up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());

        cursor.translate(dir).translate(orth);
        stairs().setUpsideDown(true).setFacing(orth).stroke(worldEditor, cursor);

        start = cursor.copy().up();
        end = start.copy().up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillars());
      }

      cursor = origin.copy().translate(dir, 6).translate(dir.antiClockwise(), 6);

      genFire(worldEditor, theme(), cursor);

      cursor = origin.copy().up(4).translate(dir);
      start = cursor.copy();
      end = cursor.copy().translate(dir, 6);
      RectSolid.newRect(start, end).fill(worldEditor, walls());
      cursor.translate(dir.antiClockwise());
      walls().stroke(worldEditor, cursor);

      start = end.copy();
      end.up(2).translate(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, walls());

      cursor = end.copy();
      start = cursor.copy().translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, walls(), true, false);

      start = cursor.copy().down();
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()), true, false);

      start = cursor.copy().translate(dir.reverse());
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stairs().setUpsideDown(true).setFacing(dir.reverse()), true, false);
    }

    generateDoorways(origin, entrances);

    return this;
  }

  public int getSize() {
    return 10;
  }

}
