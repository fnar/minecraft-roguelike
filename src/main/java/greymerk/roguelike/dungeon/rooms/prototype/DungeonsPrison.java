package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DungeonsPrison extends DungeonBase {

  public DungeonsPrison(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    Coord cursor;

    largeRoom(editor, rand, settings, origin);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      sideRoom(editor, rand, settings, cursor, dir);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(dir.left(), 3);
      pillar(editor, rand, settings, cursor, 4);
    }

    for (Cardinal dir : Cardinal.directions) {
      List<Cardinal> doors = new ArrayList<>();

      if (Arrays.asList(entrances).contains(dir)) {
        doors.add(dir.right());
      }

      if (Arrays.asList(entrances).contains(dir.left())) {
        doors.add(dir.reverse());
      }

      if (doors.isEmpty()) {
        continue;
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(dir.left(), 6);

      cell(editor, rand, settings, cursor, doors, rand.nextBoolean());
    }

    return this;
  }

  public void largeRoom(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = settings.getTheme().getPrimary().getStair();

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP, 5);
    settings.getTheme().getPrimary().getWall().fill(editor, rand, new RectHollow(start, end), false, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(dir.left(), 3);
      pillar(editor, rand, settings, cursor, 4);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.EAST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.WEST);
    RectSolid.fill(editor, rand, start, end, air);


    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 2);
      air.set(editor, cursor);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setOrientation(o.reverse(), true).set(editor, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      air.set(editor, cursor);
      cursor.translate(dir, 1);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    }
  }

  private void sideRoom(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = settings.getTheme().getPrimary().getStair();
    int height = 3;

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, height);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP, height + 1);
    settings.getTheme().getPrimary().getWall().fill(editor, rand, new RectHollow(start, end), false, true);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse(), 3);
    start.translate(dir.left());
    end.translate(dir.right());
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    for (Cardinal d : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(d, 3);
      cursor.translate(dir, 3);
      pillar(editor, rand, settings, cursor, height);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.left());
    end.translate(dir.right());
    start.translate(dir.reverse(), 3);
    end.translate(dir, 2);

    for (Cardinal d : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      start = new Coord(cursor);
      start.translate(d, 2);
      end = new Coord(start);
      start.translate(d.left(), 3);
      end.translate(d.right(), 3);
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));

      cursor.translate(Cardinal.UP, 1);
      start = new Coord(cursor);
      start.translate(d);
      end = new Coord(start);
      start.translate(d.left(), 3);
      end.translate(d.right(), 3);
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    }


    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    start = new Coord(cursor);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(dir.left(), 2);
    end.translate(dir.right(), 2);
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));

    cursor.translate(Cardinal.UP, 1);
    air.set(editor, cursor);
    start = new Coord(cursor);
    start.translate(dir, 1);
    end = new Coord(start);
    start.translate(dir.left(), 1);
    end.translate(dir.right(), 1);
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));
  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, int height) {
    Coord cursor;
    IBlockFactory pillar = settings.getTheme().getPrimary().getPillar();
    IStair stair = settings.getTheme().getPrimary().getStair();

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, height - 1);
    editor.fillDown(rand, new Coord(cursor), pillar);
    cursor.translate(Cardinal.UP);
    pillar.set(editor, rand, cursor);
    for (Cardinal dir : Cardinal.directions) {
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
    }
  }

  private void cell(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances, boolean occupied) {

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock bar = BlockType.get(BlockType.IRON_BAR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.UP, 4);
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 1);
    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.SOUTH, 1);
    end.translate(Cardinal.WEST, 1);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.left());
      end.translate(dir.right());
      end.translate(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, bar);

      if (rand.nextBoolean()) {
        air.set(editor, cursor);
        cursor.translate(Cardinal.UP);
        air.set(editor, cursor);
      }
    }

    if (occupied) {
      if (rand.nextBoolean()) {
        SpawnerSettings spawners = settings.getSpawners();
        generateSpawner(editor, rand, origin, settings.getDifficulty(origin), spawners, MobType.SKELETON);
      } else {
        SpawnerSettings spawners = settings.getSpawners();
        generateSpawner(editor, rand, origin, settings.getDifficulty(origin), spawners, MobType.SKELETON);
      }

    }
  }

  public int getSize() {
    return 12;
  }
}
