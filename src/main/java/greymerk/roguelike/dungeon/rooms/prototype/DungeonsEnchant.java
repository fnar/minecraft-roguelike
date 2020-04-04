package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
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

  @Override
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
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
    start.add(dir, 5);
    end = new Coord(start);
    start.add(new Coord(-2, 0, -2));
    end.add(new Coord(2, 3, 2));
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(dir.reverse(), 2);
    start.add(dir.left(), 4);
    end.add(dir, 2);
    end.add(dir.right(), 4);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(dir.reverse(), 2);
    end = new Coord(start);
    end.add(dir.reverse());
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    end.add(dir, 3);
    start.add(dir.left(), 3);
    end.add(dir.right(), 3);
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(dir.left(), 4);
    end.add(dir.right(), 4);
    start.add(dir.reverse(), 2);
    end.add(dir, 2);
    theme.getPrimary().getFloor().fill(editor, rand, new RectSolid(start, end));

    start = new Coord(origin);
    start.add(dir.reverse(), 4);
    end = new Coord(start);
    end.add(Cardinal.UP, 3);
    start.add(dir.left());
    end.add(dir.right());
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    cursor = new Coord(origin);
    cursor.add(dir, 5);
    for (Cardinal d : Cardinal.directions) {
      start = new Coord(cursor);
      start.add(d, 2);
      start.add(d.left(), 2);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);

      if (d == dir.reverse()) {
        continue;
      }

      start = new Coord(cursor);
      start.add(d, 3);
      end = new Coord(start);
      start.add(d.left());
      end.add(d.right());
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, panel);

      start = new Coord(cursor);
      start.add(d, 2);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      start.add(d.left());
      end.add(d.right());
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.add(d.reverse());
      start.add(Cardinal.UP);
      end.add(d.reverse());
      end.add(Cardinal.UP);
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.add(dir, 5);
    cursor.add(Cardinal.UP, 4);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);
    cursor.add(Cardinal.DOWN);
    cursor.add(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(dir.reverse());
    wall.set(editor, rand, cursor);
    cursor.add(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    wall.set(editor, rand, cursor);

    for (Cardinal d : Cardinal.directions) {

      start = new Coord(origin);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      if (d == dir) {
        end.add(d);
      } else {
        end.add(d, 2);
      }

      RectSolid.fill(editor, rand, start, end, air);

      for (Cardinal o : d.orthogonal()) {
        Coord s = new Coord(start);
        s.add(d);
        Coord e = new Coord(end);
        s.add(o);
        e.add(o);
        stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(s, e));
      }

      Coord s = new Coord(start);
      s.add(d);
      Coord e = new Coord(end);
      s.add(Cardinal.UP);
      e.add(Cardinal.UP);
      RectSolid.fill(editor, rand, s, e, wall);

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 5);
      cursor.add(d);
      stair.setOrientation(d.reverse(), true).set(editor, cursor);
      cursor.add(d.left());
      wall.set(editor, rand, cursor);

    }

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(dir, 3);
    end.add(dir, 7);
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    RectSolid.fill(editor, rand, start, end, panel);

    for (Cardinal o : dir.orthogonal()) {
      start = new Coord(origin);
      start.add(dir.reverse(), 3);
      start.add(o, 3);
      end = new Coord(start);
      end.add(dir.reverse());
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(o, 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, wall);
      cursor = new Coord(end);
      cursor.add(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(o.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);

      start = new Coord(origin);
      start.add(o, 4);
      end = new Coord(start);
      start.add(o.left());
      end.add(o.right());
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP, 3);
      end.add(Cardinal.UP, 3);
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.add(o.reverse());
      start.add(Cardinal.UP);
      end.add(o.reverse());
      end.add(Cardinal.UP);
      stair.setOrientation(o.reverse(), true).fill(editor, rand, new RectSolid(start, end));

      for (Cardinal r : o.orthogonal()) {
        start = new Coord(origin);
        start.add(o, 4);
        start.add(r, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);
      }

      start = new Coord(origin);
      start.add(o, 5);
      end = new Coord(start);
      start.add(o.left());
      end.add(o.right());
      start.add(Cardinal.UP);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, panel);

      start = new Coord(origin);
      start.add(dir.reverse(), 3);
      start.add(o, 2);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);
      cursor = new Coord(end);
      cursor.add(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(o);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.add(o);
      stair.setOrientation(dir, true).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir.reverse(), 2);
      cursor.add(o);
      cursor.add(Cardinal.UP, 4);
      wall.set(editor, rand, cursor);
      cursor.add(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);

      start = new Coord(origin);
      start.add(Cardinal.UP);
      start.add(o, 4);
      end = new Coord(start);
      start.add(o.left());
      end.add(o.right());
      chests.addAll(new RectSolid(start, end).get());
    }

    cursor = new Coord(origin);
    cursor.add(dir.reverse(), 2);
    cursor.add(Cardinal.UP, 4);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(dir.reverse());
    wall.set(editor, rand, cursor);

    cursor = new Coord(origin);
    cursor.add(dir, 5);
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
    start.add(dir.reverse(), 4);
    end.add(dir, 8);
    start.add(dir.left(), 5);
    end.add(dir.right(), 5);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 2);

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
