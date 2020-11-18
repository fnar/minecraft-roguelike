package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.FOOD;


public class DungeonMess extends DungeonBase {

  public DungeonMess(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

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
    start.translate(-8, -1, -8);
    end = new Coord(origin);
    end.translate(8, 5, 8);
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    start.translate(-2, 5, -2);
    end = new Coord(origin);
    end.translate(2, 5, 2);
    RectSolid.fill(editor, rand, start, end, panel, false, true);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal d : Cardinal.directions) {
        cursor = new Coord(end);
        cursor.translate(d);
        stair.setOrientation(d, true).set(editor, cursor);
      }

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);


      Cardinal[] corner = new Cardinal[]{dir, dir.antiClockwise()};
      if (entrances.length == 4 && dir == entrances[0]) {
        supplyCorner(editor, rand, settings, corner, origin);
      } else {
        corner(editor, rand, settings, corner, origin);
      }

      doorway(editor, rand, settings, dir, origin);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.translate(dir);
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
        supplies(editor, settings, nonDoors.get(0), origin);
      default:
    }


    return this;
  }

  private void supplyCorner(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(entrances[0], 7);
    start.translate(entrances[1], 7);
    end = new Coord(start);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[0], 2);
    cursor.translate(Cardinal.UP);

    editor.treasureChestEditor.createChest(settings.getDifficulty(cursor), cursor, false, FOOD);

    cursor = new Coord(origin);
    cursor.translate(entrances[0], 5);
    cursor.translate(entrances[1], 5);
    cursor.translate(entrances[1], 2);
    Furnace.generate(editor, true, entrances[1].reverse(), cursor);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir.reverse());
      wall.set(editor, rand, cursor);
      cursor.translate(Cardinal.DOWN);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.translate(entrances[0], 5);
      cursor.translate(entrances[1], 5);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      stair.setOrientation(dir.clockwise(), true).set(editor, start);
      stair.setOrientation(dir.antiClockwise(), true).set(editor, end);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.translate(dir);
      end.translate(dir);
      end.translate(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void corner(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
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
    start.translate(entrances[0], 7);
    start.translate(entrances[1], 7);
    end = new Coord(start);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.translate(entrances[0], 4);
    start.translate(entrances[1], 4);
    start.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.translate(entrances[0], 4);
    cursor.translate(entrances[1], 4);
    table.setOrientation(entrances[0].reverse(), true).set(editor, cursor);
    cursor.translate(entrances[1]);
    table.setOrientation(entrances[1], true).set(editor, cursor);
    cursor.translate(entrances[0]);
    table.setOrientation(entrances[0], true).set(editor, cursor);
    cursor.translate(entrances[1].reverse());
    table.setOrientation(entrances[1].reverse(), true).set(editor, cursor);


    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.translate(Cardinal.UP, 3);
      cursor.translate(dir.reverse());
      wall.set(editor, rand, cursor);
      cursor.translate(Cardinal.DOWN);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.translate(entrances[0], 5);
      cursor.translate(entrances[1], 5);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end = new Coord(cursor);
      end.translate(dir.clockwise());
      stair.setOrientation(dir.reverse(), false).fill(editor, rand, new RectSolid(start, end));
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      stair.setOrientation(dir.clockwise(), true).set(editor, start);
      stair.setOrientation(dir.antiClockwise(), true).set(editor, end);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.translate(dir);
      end.translate(dir);
      end.translate(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void doorway(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 7);
    start.translate(Cardinal.UP, 3);
    end = new Coord(start);
    end.translate(Cardinal.UP);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 7);
      cursor.translate(o, 2);
      cursor.translate(Cardinal.UP, 2);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);

      cursor.translate(dir.reverse());
      cursor.translate(Cardinal.UP, 2);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(o, true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 6);
    cursor.translate(Cardinal.UP, 3);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    start.translate(dir, 4);
    end = new Coord(start);
    end.translate(dir);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, panel, false, true);


  }

  private void fireplace(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
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
    start.translate(dir, 7);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.translate(dir, 7);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.translate(dir, 6);
    bars.set(editor, cursor);
    cursor.translate(dir);
    cursor.translate(Cardinal.DOWN);
    netherrack.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    fire.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.set(editor, cursor);
      cursor.translate(o);
      wall.set(editor, rand, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(o, false).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setOrientation(o, false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(o);
      bars.set(editor, cursor);
      cursor.translate(dir);
      cursor.translate(Cardinal.DOWN);
      netherrack.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      fire.set(editor, cursor);
    }
  }

  private void supplies(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = new Coord(origin);
    cursor.translate(dir, 7);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    editor.treasureChestEditor.createChest(settings.getDifficulty(origin), cursor, false, FOOD);
    cursor.translate(dir.antiClockwise());
    Furnace.generate(editor, dir, cursor);
    cursor.translate(dir.clockwise(), 2);
    BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 7);
      cursor.translate(o);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(o);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(o.reverse(), false).set(editor, cursor);
    }


  }

  private void sideTable(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair table = theme.getSecondary().getStair();
    Coord cursor = new Coord(origin);

    cursor.translate(dir, 5);
    table.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(dir);
    table.setOrientation(dir, true).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(o);
      table.setOrientation(o, true).set(editor, cursor);
      cursor.translate(dir);
      table.setOrientation(o, true).set(editor, cursor);
    }
  }

  public int getSize() {
    return 10;
  }
}
