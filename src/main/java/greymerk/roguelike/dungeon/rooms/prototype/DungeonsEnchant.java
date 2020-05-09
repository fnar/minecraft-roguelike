package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.ENCHANTING;
import static greymerk.roguelike.treasure.Treasure.createChests;

public class DungeonsEnchant extends DungeonBase {

  public DungeonsEnchant(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    Cardinal dir = entrances[0];

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock panel = ColorBlock.get(ColorBlock.CLAY, DyeColor.PURPLE);
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();

    List<Coord> chests = new ArrayList<Coord>();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(new Coord(-2, 0, -2));
    end.translate(new Coord(2, 3, 2));
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir, 2);
    end.translate(dir.clockwise(), 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    end.translate(dir, 3);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(dir.reverse(), 2);
    end.translate(dir, 2);
    theme.getPrimary().getFloor().fill(editor, rand, new RectSolid(start, end));

    start = new Coord(origin);
    start.translate(dir.reverse(), 4);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    for (Cardinal d : Cardinal.directions) {
      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(d.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);

      if (d == dir.reverse()) {
        continue;
      }

      start = new Coord(cursor);
      start.translate(d, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, panel);

      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.translate(d.reverse());
      start.translate(Cardinal.UP);
      end.translate(d.reverse());
      end.translate(Cardinal.UP);
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    cursor.translate(Cardinal.UP, 4);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(dir.reverse());
    wall.set(editor, rand, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.set(editor, rand, cursor);

    for (Cardinal d : Cardinal.directions) {

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      if (d == dir) {
        end.translate(d);
      } else {
        end.translate(d, 2);
      }

      RectSolid.fill(editor, rand, start, end, air);

      for (Cardinal o : d.orthogonal()) {
        Coord s = new Coord(start);
        s.translate(d);
        Coord e = new Coord(end);
        s.translate(o);
        e.translate(o);
        stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(s, e));
      }

      Coord s = new Coord(start);
      s.translate(d);
      Coord e = new Coord(end);
      s.translate(Cardinal.UP);
      e.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, s, e, wall);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(d);
      stair.setOrientation(d.reverse(), true).set(editor, cursor);
      cursor.translate(d.antiClockwise());
      wall.set(editor, rand, cursor);

    }

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 3);
    end.translate(dir, 7);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, panel);

    for (Cardinal o : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(dir.reverse());
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, wall);
      cursor = new Coord(end);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);

      start = new Coord(origin);
      start.translate(o, 4);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP, 3);
      end.translate(Cardinal.UP, 3);
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.translate(o.reverse());
      start.translate(Cardinal.UP);
      end.translate(o.reverse());
      end.translate(Cardinal.UP);
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));

      for (Cardinal r : o.orthogonal()) {
        start = new Coord(origin);
        start.translate(o, 4);
        start.translate(r, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);
      }

      start = new Coord(origin);
      start.translate(o, 5);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, panel);

      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);
      cursor = new Coord(end);
      cursor.translate(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(o);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.translate(o);
      stair.setOrientation(dir, true).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir.reverse(), 2);
      cursor.translate(o);
      cursor.translate(Cardinal.UP, 4);
      wall.set(editor, rand, cursor);
      cursor.translate(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP);
      start.translate(o, 4);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      chests.addAll(new RectSolid(start, end).get());
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 2);
    cursor.translate(Cardinal.UP, 4);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(dir.reverse());
    wall.set(editor, rand, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    BlockType.get(BlockType.ENCHANTING_TABLE).set(editor, cursor);

    List<Coord> chestLocations = chooseRandomLocations(rand, 1, chests);
    createChests(editor, rand, settings.getDifficulty(origin), chestLocations, false, ENCHANTING);

    return this;
  }

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.translate(dir.reverse(), 4);
    end.translate(dir, 8);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 2);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  public int getSize() {
    return 6;
  }
}
