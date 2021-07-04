package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsFire extends DungeonBase {


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
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Theme theme = levelSettings.getTheme();

    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush pillar = theme.getPrimary().getPillar();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.north(8);
    start.west(8);
    start.down();
    end = origin.copy();
    end.south(8);
    end.east(8);
    end.up(7);

    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    start.down();
    end = start.copy();
    start.north(8);
    start.west(8);
    end.south(8);
    end.east(8);
    RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getFloor(), false, true);

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orth : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 7);
        start.translate(orth, 2);
        end = start.copy();
        end.up(6);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

        cursor = origin.copy();
        cursor.translate(dir, 8);
        cursor.translate(orth);
        cursor.up(2);
        stair.setUpsideDown(true).setFacing(orth.reverse()).stroke(worldEditor, cursor, true, false);

        cursor.translate(dir.reverse());
        cursor.up();
        stair.setUpsideDown(true).setFacing(orth.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

        cursor.translate(dir.reverse());
        cursor.translate(orth);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

        cursor.translate(dir);
        cursor.translate(orth);
        stair.setUpsideDown(true).setFacing(orth).stroke(worldEditor, cursor);

        start = cursor.copy();
        start.up();
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

      }

      cursor = origin.copy();
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);

      genFire(worldEditor, theme, cursor);

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);
      cursor.translate(dir.antiClockwise());
      wall.stroke(worldEditor, cursor);

      start = end.copy();
      end.up(2);
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      stair.setUpsideDown(true).setFacing(dir.reverse());

      cursor = end.copy();
      start = cursor.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall, true, false);

      start = cursor.copy();
      start.down();
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stair, true, false);

      start = cursor.copy();
      start.translate(dir.reverse());
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, stair, true, false);
    }

    generateDoorways(origin, entrances);

    return this;
  }

  public int getSize() {
    return 8;
  }

}
