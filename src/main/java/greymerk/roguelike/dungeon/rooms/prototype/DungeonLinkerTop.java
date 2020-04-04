package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonLinkerTop extends DungeonBase {

  public DungeonLinkerTop() {

  }

  public DungeonLinkerTop(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-4, -1, -4));
    end.add(new Coord(4, 5, 4));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 5);
    settings.getTheme().getPrimary().getLightBlock().set(editor, rand, cursor);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-4, -1, -4));
    end.add(new Coord(4, -1, 4));
    RectSolid.fill(editor, rand, start, end, floor, true, true);

    for (Cardinal dir : Cardinal.directions) {

      start = new Coord(origin);
      end = new Coord(origin);
      start.add(dir, 3);
      start.add(dir.left(), 3);
      end.add(dir, 4);
      end.add(dir.left(), 4);
      end.add(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(dir.left(), 2);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      end.add(dir.right(), 4);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.add(dir.reverse());
      end.add(dir.reverse());
      RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir.reverse(), true), true, true);

      for (Cardinal o : dir.orthogonal()) {
        cursor = new Coord(origin);
        cursor.add(dir, 3);
        cursor.add(Cardinal.UP, 2);
        cursor.add(o, 2);
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        wall.set(editor, rand, cursor);
        cursor.add(o.reverse());
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
      }
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
