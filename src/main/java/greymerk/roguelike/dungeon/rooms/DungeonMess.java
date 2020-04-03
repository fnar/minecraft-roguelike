package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.FOOD;
import static greymerk.roguelike.treasure.Treasure.createChest;


public class DungeonMess extends DungeonBase {

  public DungeonMess(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory panel = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    // clear air
    start = new Coord(origin);
    start.add(new Coord(-8, -1, -8));
    end = new Coord(origin);
    end.add(new Coord(8, 5, 8));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    start.add(new Coord(-2, 5, -2));
    end = new Coord(origin);
    end.add(new Coord(2, 5, 2));
    RectSolid.fill(editor, rand, start, end, panel, false, true);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 3);
      start.add(dir.left(), 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal d : Cardinal.directions) {
        cursor = new Coord(end);
        cursor.add(d);
        stair.setOrientation(d, true).set(editor, cursor);
      }

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      start.add(dir.left(), 3);
      end.add(dir.right(), 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);


      Cardinal[] corner = new Cardinal[]{dir, dir.left()};
      if (entrances.length == 4 && dir == entrances[0]) {
        supplyCorner(editor, rand, settings, corner, origin);
      } else {
        corner(editor, rand, settings, corner, origin);
      }

      doorway(editor, rand, settings, dir, origin);

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    }

    List<Cardinal> nonDoors = new ArrayList<Cardinal>();
    for (Cardinal dir : Cardinal.directions) {
      if (!Arrays.asList(entrances).contains(dir)) {
        nonDoors.add(dir);
      }
    }

    Collections.shuffle(nonDoors);

    switch (nonDoors.size()) {
      case 3:
        sideTable(editor, rand, settings, nonDoors.get(2), origin);
      case 2:
        fireplace(editor, rand, settings, nonDoors.get(1), origin);
      case 1:
        supplies(editor, rand, settings, nonDoors.get(0), origin);
      default:
    }


    return false;
  }

  private void supplyCorner(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(entrances[0], 7);
    start.add(entrances[1], 7);
    end = new Coord(start);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(entrances[0], 4);
    start.add(entrances[1], 4);
    start.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.add(entrances[0], 5);
    cursor.add(entrances[1], 5);
    cursor.add(entrances[0], 2);
    cursor.add(Cardinal.UP);

    createChest(editor, rand, settings.getDifficulty(cursor), cursor, false, FOOD);

    cursor = new Coord(origin);
    cursor.add(entrances[0], 5);
    cursor.add(entrances[1], 5);
    cursor.add(entrances[1], 2);
    Furnace.generate(editor, true, entrances[1].reverse(), cursor);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(entrances[0], 3);
      cursor.add(entrances[1], 3);
      cursor.add(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(Cardinal.UP, 3);
      cursor.add(dir.reverse());
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.DOWN);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(dir.reverse());
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.add(entrances[0], 5);
      cursor.add(entrances[1], 5);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      start.add(dir.left());
      end = new Coord(cursor);
      end.add(dir.right());
      stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      stair.setOrientation(dir.right(), true).set(editor, start);
      stair.setOrientation(dir.left(), true).set(editor, end);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.add(dir);
      end.add(dir);
      end.add(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void corner(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    IStair table = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(entrances[0], 7);
    start.add(entrances[1], 7);
    end = new Coord(start);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(entrances[0], 4);
    start.add(entrances[1], 4);
    start.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.add(entrances[0], 4);
    cursor.add(entrances[1], 4);
    table.setOrientation(entrances[0].reverse(), true).set(editor, cursor);
    cursor.add(entrances[1]);
    table.setOrientation(entrances[1], true).set(editor, cursor);
    cursor.add(entrances[0]);
    table.setOrientation(entrances[0], true).set(editor, cursor);
    cursor.add(entrances[1].reverse());
    table.setOrientation(entrances[1].reverse(), true).set(editor, cursor);


    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(entrances[0], 3);
      cursor.add(entrances[1], 3);
      cursor.add(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(Cardinal.UP, 3);
      cursor.add(dir.reverse());
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.DOWN);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(dir.reverse());
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.add(entrances[0], 5);
      cursor.add(entrances[1], 5);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      start.add(dir.left());
      end = new Coord(cursor);
      end.add(dir.right());
      stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      stair.setOrientation(dir.right(), true).set(editor, start);
      stair.setOrientation(dir.left(), true).set(editor, end);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.add(dir);
      end.add(dir);
      end.add(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void doorway(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 7);
    start.add(Cardinal.UP, 3);
    end = new Coord(start);
    end.add(Cardinal.UP);
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(dir, 7);
      cursor.add(o, 2);
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);

      cursor.add(dir.reverse());
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(o.reverse());
      stair.setOrientation(o, true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    cursor.add(Cardinal.UP, 3);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.add(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);

    start = new Coord(origin);
    start.add(Cardinal.UP, 5);
    start.add(dir, 4);
    end = new Coord(start);
    end.add(dir);
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    RectSolid.fill(editor, rand, start, end, panel, false, true);


  }

  private void fireplace(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
    MetaBlock fire = BlockType.get(BlockType.FIRE);
    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 7);
    end = new Coord(start);
    start.add(dir.left(), 2);
    end.add(dir.right(), 2);
    end.add(Cardinal.UP, 2);
    end.add(dir);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(dir, 7);
    end = new Coord(start);
    start.add(dir.left());
    end.add(dir.right());
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    bars.set(editor, cursor);
    cursor.add(dir);
    cursor.add(Cardinal.DOWN);
    netherrack.set(editor, cursor);
    cursor.add(Cardinal.UP);
    fire.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);
    cursor.add(dir.reverse());
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(o);
      bars.set(editor, cursor);
      cursor.add(o);
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(o, false).set(editor, cursor);
      cursor.add(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(dir.reverse());
      stair.setOrientation(o, false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(o);
      bars.set(editor, cursor);
      cursor.add(dir);
      cursor.add(Cardinal.DOWN);
      netherrack.set(editor, cursor);
      cursor.add(Cardinal.UP);
      fire.set(editor, cursor);
    }
  }

  private void supplies(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = new Coord(origin);
    cursor.add(dir, 7);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    createChest(editor, rand, settings.getDifficulty(origin), cursor, false, FOOD);
    cursor.add(dir.left());
    Furnace.generate(editor, dir, cursor);
    cursor.add(dir.right(), 2);
    BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(dir, 7);
      cursor.add(o);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.add(o);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(o.reverse(), false).set(editor, cursor);
    }


  }

  private void sideTable(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair table = theme.getSecondary().getStair();
    Coord cursor = new Coord(origin);

    cursor.add(dir, 5);
    table.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(dir);
    table.setOrientation(dir, true).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(dir, 5);
      cursor.add(o);
      table.setOrientation(o, true).set(editor, cursor);
      cursor.add(dir);
      table.setOrientation(o, true).set(editor, cursor);
    }
  }

  public int getSize() {
    return 10;
  }
}
