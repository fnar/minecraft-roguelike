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

public class DungeonsSlime extends DungeonBase {

  public DungeonsSlime(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    IBlockFactory liquid = theme.getPrimary().getLiquid();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 5, 8));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      corner(editor, rand, settings, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 8);
      end.translate(dir.clockwise(), 8);
      RectSolid.fill(editor, rand, start, end, wall);
      start.translate(dir, 4);
      end.translate(dir, 4);
      RectSolid.fill(editor, rand, start, end, wall);

    }


    for (Cardinal dir : Cardinal.directions) {
      if (!Arrays.asList(entrances).contains(dir)) {
        start = new Coord(origin);
        start.translate(dir, 4);
        end = new Coord(start);
        end.translate(dir, 2);
        start.translate(dir.antiClockwise(), 3);
        end.translate(dir.clockwise(), 3);
        RectSolid.fill(editor, rand, start, end, air);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, liquid);
        start.translate(Cardinal.DOWN);
        end.translate(Cardinal.DOWN);
        RectSolid.fill(editor, rand, start, end, wall);

        start = new Coord(origin);
        start.translate(dir, 3);
        end = new Coord(start);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir.clockwise(), 2);
        RectSolid.fill(editor, rand, start, end, bars);

        cursor = new Coord(origin);
        cursor.translate(dir, 7);
        wall.set(editor, rand, cursor);
        cursor.translate(Cardinal.UP, 2);
        wall.set(editor, rand, cursor);
        cursor.translate(Cardinal.DOWN);
        cursor.translate(dir);
        liquid.set(editor, rand, cursor);
        for (Cardinal o : dir.orthogonal()) {
          cursor = new Coord(origin);
          cursor.translate(dir, 7);
          cursor.translate(o);
          stair.setOrientation(o, true).set(editor, cursor);
          cursor.translate(Cardinal.UP);
          wall.set(editor, rand, cursor);
          cursor.translate(Cardinal.UP);
          stair.setOrientation(o, false).set(editor, cursor);

        }
      }
    }


    return this;
  }

  private void corner(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    IBlockFactory water = theme.getPrimary().getLiquid();


    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-1, -1, -1));
    end.translate(new Coord(1, -1, 1));
    RectSolid.fill(editor, rand, start, end, water);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-1, -2, -1));
    end.translate(new Coord(1, -2, 1));
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 2);
      start.translate(dir.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);

      for (Cardinal d : Cardinal.directions) {
        cursor = new Coord(end);
        cursor.translate(d);
        stair.setOrientation(d, true).set(editor, rand, cursor, true, false);
      }

      start = new Coord(origin);
      start.translate(dir, 2);
      end = new Coord(start);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      RectSolid.fill(editor, rand, start, end, bars);

    }
  }

  public int getSize() {
    return 8;
  }
}
