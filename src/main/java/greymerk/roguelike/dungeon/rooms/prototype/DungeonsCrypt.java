package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Arrays;
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
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.treasure.Treasure.COMMON_TREASURES;


public class DungeonsCrypt extends DungeonBase {

  public DungeonsCrypt(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 4, 3));
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, -1, -9));
    end.translate(new Coord(9, -1, 9));
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, 5, -9));
    end.translate(new Coord(9, 6, 9));
    RectSolid.fill(editor, rand, start, end, walls, false, true);

    for (Cardinal dir : Cardinal.directions) {

      List<?> doorways = Arrays.asList(entrances);

      if (doorways.contains(dir) && doorways.contains(dir.antiClockwise())) {
        start = new Coord(origin);
        end = new Coord(origin);
        start.translate(dir, 3);
        end.translate(dir.antiClockwise(), 5);
        end.translate(dir, 5);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, air);
      }

      if (doorways.contains(dir)) {
        // doorway air
        start = new Coord(origin);
        end = new Coord(origin);
        start.translate(dir, 3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir, 8);
        end.translate(dir.clockwise(), 2);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, air);

        for (Cardinal o : dir.orthogonal()) {
          if (doorways.contains(o)) {

            cursor = new Coord(origin);
            cursor.translate(dir, 7);
            cursor.translate(o, 3);
            cursor.translate(Cardinal.UP);

            crypt(editor, rand, settings, cursor, o);
          } else {

            start = new Coord(origin);
            end = new Coord(origin);
            start.translate(dir, 4);
            start.translate(o, 3);
            end.translate(dir, 8);
            end.translate(o, 8);
            end.translate(Cardinal.UP, 4);
            RectSolid.fill(editor, rand, start, end, air);

            cursor = new Coord(origin);
            cursor.translate(dir, 6);
            cursor.translate(o, 3);
            cursor.translate(Cardinal.UP);

            sarcophagus(editor, rand, settings, cursor, o);
          }
        }

      } else {
        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        mausoleumWall(editor, rand, settings, cursor, dir);
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(editor, rand, settings, cursor);

      start = new Coord(origin);
      start.translate(dir, 8);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setOrientation(dir.reverse(), true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    return this;
  }

  private void sarcophagus(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    cursor.translate(Cardinal.UP, 3);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    start = new Coord(origin);

    for (Cardinal o : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(Cardinal.DOWN);
      start.translate(dir);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(dir, 4);
      end.translate(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, walls);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir, 5);
      cursor.translate(o, 2);
      pillar(editor, rand, settings, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(dir, 3);
      stair.setOrientation(o.reverse(), true);
      RectSolid.fill(editor, rand, start, end, stair);
    }

    cursor = new Coord(origin);
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);
    cursor.translate(Cardinal.DOWN, 2);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(dir);
    walls.set(editor, rand, cursor);
    cursor.translate(dir);
    walls.set(editor, rand, cursor);
    cursor.translate(dir);
    stair.setOrientation(dir, false).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir, false).set(editor, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(o);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 3);
      stair.setOrientation(o, false);
      RectSolid.fill(editor, rand, start, end, stair);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      stair.setOrientation(o, true);
      RectSolid.fill(editor, rand, start, end, stair);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      stair.setOrientation(o, false);
      RectSolid.fill(editor, rand, start, end, stair);
    }

  }

  private void crypt(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(dir.antiClockwise());
    end = new Coord(origin);
    end.translate(Cardinal.UP, 3);
    end.translate(dir.clockwise());
    end.translate(dir, 3);

    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP, 2);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    walls.set(editor, rand, cursor);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(dir.reverse());
      cursor.translate(Cardinal.UP);
      cursor.translate(o);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      walls.set(editor, rand, cursor);
      cursor.translate(Cardinal.UP);
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(dir.reverse(), 2);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(dir, 7);
      stair.setOrientation(o, true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    stair.setOrientation(dir.reverse(), true);
    RectSolid.fill(editor, rand, start, end, stair);

    tomb(editor, rand, settings, origin, dir);
  }

  private void mausoleumWall(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();
    IBlockFactory walls = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    end.translate(dir, 4);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Cardinal.UP, 2);
    tomb(editor, rand, settings, cursor, dir);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP);
      cursor.translate(o, 2);
      tomb(editor, rand, settings, cursor, dir);

      cursor.translate(Cardinal.UP, 2);
      tomb(editor, rand, settings, cursor, dir);
    }

  }

  private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, walls);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setOrientation(dir, true);
      stair.set(editor, rand, cursor, true, false);
    }
  }

  private void tomb(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();
    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    MetaBlock tombStone = BlockType.get(BlockType.QUARTZ);
    MetaBlock air = BlockType.get(BlockType.AIR);

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    cursor.translate(Cardinal.UP);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    cursor.translate(dir.reverse());
    stair.setOrientation(dir, true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    RectSolid.fill(editor, rand, origin, cursor, air);

    if (rand.nextInt(4) == 0) {
      return;
    }

    cursor = new Coord(origin);
    tombStone.set(editor, cursor);

    if (rand.nextInt(5) != 0) {
      return;
    }

    cursor.translate(dir);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, rand, cursor, settings.getDifficulty(cursor), spawners, MobType.UNDEAD_MOBS);

    cursor.translate(dir);
    editor.treasureChestEditor.createChest(settings.getDifficulty(cursor), cursor, false, COMMON_TREASURES);
  }

  public int getSize() {
    return 10;
  }
}
