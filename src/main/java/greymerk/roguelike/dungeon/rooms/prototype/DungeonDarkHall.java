package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonDarkHall extends DungeonBase {

  public DungeonDarkHall(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    IBlockFactory outerWall = theme.getPrimary().getWall();
    IBlockFactory wall = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();
    IStair stair = theme.getSecondary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 7);
    start.translate(Cardinal.WEST, 7);
    end.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.EAST, 7);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 7);

    RectHollow.fill(editor, rand, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    start.translate(Cardinal.UP, 6);
    end.translate(Cardinal.UP, 9);

    RectHollow.fill(editor, rand, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);

    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : entrances) {
      Cardinal[] orth = dir.orthogonal();
      start = new Coord(origin);
      start.translate(orth[0]);
      end = new Coord(origin);
      end.translate(orth[1]);
      end.translate(dir, 7);
      RectSolid.fill(editor, rand, start, end, theme.getSecondary().getFloor(), false, true);
    }

    for (Cardinal dir : Cardinal.directions) {

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(dir.left(), 6);
      end = new Coord(start);
      end.translate(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      start.translate(dir.left(), 6);
      end.translate(dir.right(), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      start.translate(dir.left(), 3);
      end.translate(dir.right(), 3);
      RectSolid.fill(editor, rand, start, end, wall);
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 7);
      pillar.set(editor, rand, start);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      end.translate(dir.reverse(), 3);
      RectSolid.fill(editor, rand, start, end, wall);

      if (Arrays.asList(entrances).contains(dir)) {
        start = new Coord(origin);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        start.translate(dir.left(), 2);
        end.translate(dir.right(), 2);
        RectSolid.fill(editor, rand, start, end, wall);

        cursor = new Coord(origin);
        cursor.translate(dir, 7);
        cursor.translate(Cardinal.UP, 2);
        air.set(editor, cursor);

        for (Cardinal o : dir.orthogonal()) {
          cursor = new Coord(origin);
          cursor.translate(dir, 7);
          cursor.translate(Cardinal.UP, 2);
          cursor.translate(o);
          stair.setOrientation(o.reverse(), true).set(editor, cursor);

          cursor = new Coord(origin);
          cursor.translate(dir, 6);
          cursor.translate(o, 3);
          pillar(editor, rand, settings, o.reverse(), cursor);

          cursor = new Coord(origin);
          cursor.translate(dir, 7);
          cursor.translate(o, 2);
          pillar.set(editor, rand, cursor);
          cursor.translate(Cardinal.UP);
          pillar.set(editor, rand, cursor);
        }
      } else {
        cursor = new Coord(origin);
        cursor.translate(dir, 6);
        pillar(editor, rand, settings, dir.reverse(), cursor);
      }

      start = new Coord(origin);
      start.translate(dir, 6);
      start.translate(Cardinal.UP, 6);
      end = new Coord(start);
      end.translate(dir.reverse(), 2);
      RectSolid.fill(editor, rand, start, end, wall);

      for (Cardinal o : dir.orthogonal()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 6);
        cursor.translate(o, 3);
        pillar(editor, rand, settings, dir.reverse(), cursor);
        start = new Coord(cursor);
        start.translate(Cardinal.UP, 6);
        end = new Coord(start);
        end.translate(dir.reverse(), 6);
        RectSolid.fill(editor, rand, start, end, wall);
      }
    }

    return this;
  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory wall = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();
    IStair stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, pillar);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    cursor.translate(dir);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);
    cursor.translate(dir);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);
    cursor.translate(dir);
    if (editor.isAirBlock(cursor)) {
      stair.setOrientation(dir, true).set(editor, cursor);
    } else {
      wall.set(editor, rand, cursor);
    }

  }

  @Override
  public int getSize() {
    return 9;
  }


}
