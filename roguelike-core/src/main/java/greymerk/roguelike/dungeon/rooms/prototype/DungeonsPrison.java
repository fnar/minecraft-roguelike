package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import com.github.fnar.minecraft.block.spawner.MobType;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DungeonsPrison extends BaseRoom {

  public DungeonsPrison(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Coord cursor;

    Random rand = worldEditor.getRandom();
    largeRoom(worldEditor, levelSettings, origin);

    for (Direction dir : entrances) {
      cursor = origin.copy();
      cursor.translate(dir, 6);
      sideRoom(worldEditor, levelSettings, cursor, dir);
    }

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(worldEditor, levelSettings, cursor, 4);
    }

    for (Direction dir : Direction.CARDINAL) {
      List<Direction> doors = new ArrayList<>();

      if (entrances.contains(dir)) {
        doors.add(dir.clockwise());
      }

      if (entrances.contains(dir.antiClockwise())) {
        doors.add(dir.reverse());
      }

      if (doors.isEmpty()) {
        continue;
      }

      cursor = origin.copy();
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
    start = origin.copy();
    start.up(6);
    end = start.copy();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);
    RectSolid.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = origin.copy();
    start.down();
    end = start.copy();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    start = origin.copy();
    end = origin.copy();
    start.north(3);
    start.west(3);
    end.south(3);
    end.east(3);
    end.up(4);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.north(4);
    start.west(4);
    end.south(4);
    end.east(4);
    end.up(5);
    settings.getTheme().getPrimary().getWall().fill(editor, new RectHollow(start, end), false, true);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(editor, settings, cursor, 4);
    }

    start = origin.copy();
    start.up(5);
    end = start.copy();
    start.north();
    start.east();
    end.south();
    end.west();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);


    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.up(5);
      cursor.translate(dir, 2);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      }

      cursor = origin.copy();
      cursor.up(6);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(dir, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    }
  }

  private void sideRoom(WorldEditor editor, LevelSettings settings, Coord origin, Direction dir) {

    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = settings.getTheme().getPrimary().getStair();
    int height = 3;

    BlockBrush wall = settings.getTheme().getPrimary().getWall();
    start = origin.copy();
    start.up(6);
    end = start.copy();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);
    RectSolid.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = origin.copy();
    start.down();
    end = start.copy();
    start.north(3);
    start.east(3);
    end.south(3);
    end.west(3);
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    start = origin.copy();
    end = origin.copy();
    start.north(3);
    start.west(3);
    end.south(3);
    end.east(3);
    end.up(height);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.north(4);
    start.west(4);
    end.south(4);
    end.east(4);
    end.up(height + 1);
    settings.getTheme().getPrimary().getWall().fill(editor, new RectHollow(start, end), false, true);

    start = origin.copy();
    start.up(4);
    end = start.copy();
    start.translate(dir);
    end.translate(dir.reverse(), 3);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.up();
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    for (Direction d : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(d, 3);
      cursor.translate(dir, 3);
      pillar(editor, settings, cursor, height);
    }

    start = origin.copy();
    start.up(4);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    start.translate(dir.reverse(), 3);
    end.translate(dir, 2);

    for (Direction d : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.up(4);
      start = cursor.copy();
      start.translate(d, 2);
      end = start.copy();
      start.translate(d.antiClockwise(), 3);
      end.translate(d.clockwise(), 3);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));

      cursor.up(1);
      start = cursor.copy();
      start.translate(d);
      end = start.copy();
      start.translate(d.antiClockwise(), 3);
      end.translate(d.clockwise(), 3);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));
    }


    cursor = origin.copy();
    cursor.up(4);
    start = cursor.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));

    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    start = cursor.copy();
    start.translate(dir, 1);
    end = start.copy();
    start.translate(dir.antiClockwise(), 1);
    end.translate(dir.clockwise(), 1);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin, int height) {
    Coord cursor;
    BlockBrush pillar = settings.getTheme().getPrimary().getPillar();
    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    cursor = origin.copy();
    cursor.up(height - 1);
    editor.fillDown(cursor.copy(), pillar);
    cursor.up();
    pillar.stroke(editor, cursor);
    for (Direction dir : Direction.CARDINAL) {
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
    }
  }

  private void cell(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Direction> entrances, boolean occupied) {

    Coord start;
    Coord end;
    Coord cursor;

    cursor = origin.copy();
    cursor.down();
    if (editor.isAirBlock(cursor)) {
      return;
    }

    BlockBrush wall = settings.getTheme().getPrimary().getWall();
    BlockBrush bar = BlockType.IRON_BAR.getBrush();

    start = origin.copy();
    end = origin.copy();
    start.down();
    start.north(2);
    start.west(2);
    end.south(2);
    end.east(2);
    end.up(4);
    RectHollow.newRect(start, end).fill(editor, wall, false, true);

    BlockBrush floor = settings.getTheme().getPrimary().getFloor();
    start = origin.copy();
    start.down();
    end = start.copy();
    start.north();
    start.east();
    end.south();
    end.west();
    RectSolid.newRect(start, end).fill(editor, floor, false, true);

    for (Direction dir : entrances) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.antiClockwise());
      end.translate(dir.clockwise());
      end.up(2);
      RectSolid.newRect(start, end).fill(editor, bar);

      if (rand.nextBoolean()) {
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.up();
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
