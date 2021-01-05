package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class DungeonsCrypt extends DungeonBase {

  public DungeonsCrypt(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {
    Random rand = editor.getRandom();
    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 4, 3));
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, -1, -9));
    end.translate(new Coord(9, -1, 9));
    RectSolid.fill(editor, start, end, floor);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, 5, -9));
    end.translate(new Coord(9, 6, 9));
    RectSolid.fill(editor, start, end, walls, false, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      if (entrances.contains(dir) && entrances.contains(dir.antiClockwise())) {
        start = new Coord(origin);
        end = new Coord(origin);
        start.translate(dir, 3);
        end.translate(dir.antiClockwise(), 5);
        end.translate(dir, 5);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
      }

      if (entrances.contains(dir)) {
        // doorway air
        start = new Coord(origin);
        end = new Coord(origin);
        start.translate(dir, 3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir, 8);
        end.translate(dir.clockwise(), 2);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

        for (Cardinal o : dir.orthogonals()) {
          if (entrances.contains(o)) {

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
            RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);

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
      pillar(editor, settings, cursor);

      start = new Coord(origin);
      start.translate(dir, 8);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      RectSolid.fill(editor, start, end, stair, true, false);
    }

    return this;
  }

  private void sarcophagus(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ThemeBase theme = settings.getTheme();

    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    cursor.translate(Cardinal.UP, 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(Cardinal.DOWN);
      start.translate(dir);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(dir, 4);
      end.translate(Cardinal.UP, 4);
      RectSolid.fill(editor, start, end, walls);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir, 5);
      cursor.translate(o, 2);
      pillar(editor, settings, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(dir, 3);
      stair.setUpsideDown(true).setFacing(o.reverse());
      RectSolid.fill(editor, start, end, stair);
    }

    cursor = new Coord(origin);
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN, 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    walls.stroke(editor, cursor);
    cursor.translate(dir);
    walls.stroke(editor, cursor);
    cursor.translate(dir);
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(o);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.translate(dir, 3);
      stair.setUpsideDown(false).setFacing(o);
      RectSolid.fill(editor, start, end, stair);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o);
      RectSolid.fill(editor, start, end, stair);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(o);
      RectSolid.fill(editor, start, end, stair);
    }

  }

  private void crypt(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ThemeBase theme = settings.getTheme();

    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

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

    RectSolid.fill(editor, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP, 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    walls.stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(dir.reverse());
      cursor.translate(Cardinal.UP);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      walls.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      walls.stroke(editor, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 3);
      start.translate(dir.reverse(), 2);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(dir, 7);
      stair.setUpsideDown(true).setFacing(o);
      RectSolid.fill(editor, start, end, stair, true, false);
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.reverse());
    RectSolid.fill(editor, start, end, stair);

    tomb(editor, rand, settings, origin, dir);
  }

  private void mausoleumWall(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ThemeBase theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    end.translate(dir, 4);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, start, end, walls);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Cardinal.UP, 2);
    tomb(editor, rand, settings, cursor, dir);

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP);
      cursor.translate(o, 2);
      tomb(editor, rand, settings, cursor, dir);

      cursor.translate(Cardinal.UP, 2);
      tomb(editor, rand, settings, cursor, dir);
    }

  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin) {

    ThemeBase theme = settings.getTheme();

    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.translate(Cardinal.UP, 4);
    RectSolid.fill(editor, start, end, walls);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir);
      stair.stroke(editor, cursor, true, false);
    }
  }

  private void tomb(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ThemeBase theme = settings.getTheme();
    Coord cursor;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush tombStone = BlockType.QUARTZ.getBrush();

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    cursor.translate(Cardinal.UP);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    RectSolid.fill(editor, origin, cursor, SingleBlockBrush.AIR);

    if (rand.nextInt(4) == 0) {
      return;
    }

    cursor = new Coord(origin);
    tombStone.stroke(editor, cursor);

    if (rand.nextInt(5) != 0) {
      return;
    }

    cursor.translate(dir);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, cursor, settings.getDifficulty(cursor), spawners, MobType.UNDEAD_MOBS);

    cursor.translate(dir);
    editor.getTreasureChestEditor().createChest(settings.getDifficulty(cursor), cursor, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(rand, ChestType.COMMON_TREASURES)));
  }

  public int getSize() {
    return 10;
  }
}
