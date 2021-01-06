package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DungeonsPrison extends DungeonBase {

  public DungeonsPrison(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    Coord cursor;

    Random rand = worldEditor.getRandom();
    largeRoom(worldEditor, levelSettings, origin);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      sideRoom(worldEditor, levelSettings, cursor, dir);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(worldEditor, levelSettings, cursor, 4);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      List<Cardinal> doors = new ArrayList<>();

      if (entrances.contains(dir)) {
        doors.add(dir.clockwise());
      }

      if (entrances.contains(dir.antiClockwise())) {
        doors.add(dir.reverse());
      }

      if (doors.isEmpty()) {
        continue;
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 6);
      cursor.translate(dir.antiClockwise(), 6);

      cell(worldEditor, rand, levelSettings, cursor, doors, rand.nextBoolean());
    }

    return this;
  }

  public void largeRoom(WorldEditor editor, LevelSettings settings, Coord origin) {
    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    BlockBrush wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, 4);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP, 5);
    settings.getTheme().getPrimary().getWall().fill(editor, new RectHollow(start, end), false, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(editor, settings, cursor, 4);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.EAST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.WEST);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);


    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 2);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      }

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 6);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(dir, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    }
  }

  private void sideRoom(WorldEditor editor, LevelSettings settings, Coord origin, Cardinal dir) {

    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = settings.getTheme().getPrimary().getStair();
    int height = 3;

    BlockBrush wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.WEST, 3);
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP, height);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP, height + 1);
    settings.getTheme().getPrimary().getWall().fill(editor, new RectHollow(start, end), false, true);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse(), 3);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    for (Cardinal d : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(d, 3);
      cursor.translate(dir, 3);
      pillar(editor, settings, cursor, height);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    start.translate(dir.reverse(), 3);
    end.translate(dir, 2);

    for (Cardinal d : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      start = new Coord(cursor);
      start.translate(d, 2);
      end = new Coord(start);
      start.translate(d.antiClockwise(), 3);
      end.translate(d.clockwise(), 3);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));

      cursor.translate(Cardinal.UP, 1);
      start = new Coord(cursor);
      start.translate(d);
      end = new Coord(start);
      start.translate(d.antiClockwise(), 3);
      end.translate(d.clockwise(), 3);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));
    }


    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    start = new Coord(cursor);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));

    cursor.translate(Cardinal.UP, 1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    start = new Coord(cursor);
    start.translate(dir, 1);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 1);
    end.translate(dir.clockwise(), 1);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin, int height) {
    Coord cursor;
    BlockBrush pillar = settings.getTheme().getPrimary().getPillar();
    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, height - 1);
    editor.fillDown(new Coord(cursor), pillar);
    cursor.translate(Cardinal.UP);
    pillar.stroke(editor, cursor);
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
    }
  }

  private void cell(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances, boolean occupied) {

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    BlockBrush wall = settings.getTheme().getPrimary().getWall();
    BlockBrush bar = BlockType.IRON_BAR.getBrush();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.UP, 4);
    RectHollow.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 1);
    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.SOUTH, 1);
    end.translate(Cardinal.WEST, 1);
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, bar);

      if (rand.nextBoolean()) {
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        SingleBlockBrush.AIR.stroke(editor, cursor);
      }
    }

    if (occupied) {
      generateSpawner(origin, MobType.SKELETON);
    }
  }

  public int getSize() {
    return 12;
  }
}
