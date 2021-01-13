package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;

public class DungeonsCrypt extends DungeonBase {

  public DungeonsCrypt(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    Random rand = worldEditor.getRandom();
    ThemeBase theme = levelSettings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 4, 3));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-9, -1, -9));
    end.translate(new Coord(9, -1, 9));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-9, 5, -9));
    end.translate(new Coord(9, 6, 9));
    RectSolid.newRect(start, end).fill(worldEditor, walls, false, true);

    for (Direction dir : Direction.CARDINAL) {

      if (entrances.contains(dir) && entrances.contains(dir.antiClockwise())) {
        start = origin.copy();
        end = origin.copy();
        start.translate(dir, 3);
        end.translate(dir.antiClockwise(), 5);
        end.translate(dir, 5);
        end.translate(Direction.UP, 4);
        RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
      }

      if (entrances.contains(dir)) {
        // doorway air
        start = origin.copy();
        end = origin.copy();
        start.translate(dir, 3);
        start.translate(dir.antiClockwise(), 2);
        end.translate(dir, 8);
        end.translate(dir.clockwise(), 2);
        end.translate(Direction.UP, 4);
        RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

        for (Direction o : dir.orthogonals()) {
          if (entrances.contains(o)) {

            cursor = origin.copy();
            cursor.translate(dir, 7);
            cursor.translate(o, 3);
            cursor.translate(Direction.UP);

            crypt(worldEditor, rand, levelSettings, cursor, o);
          } else {

            start = origin.copy();
            end = origin.copy();
            start.translate(dir, 4);
            start.translate(o, 3);
            end.translate(dir, 8);
            end.translate(o, 8);
            end.translate(Direction.UP, 4);
            RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

            cursor = origin.copy();
            cursor.translate(dir, 6);
            cursor.translate(o, 3);
            cursor.translate(Direction.UP);

            sarcophagus(worldEditor, rand, levelSettings, cursor, o);
          }
        }

      } else {
        cursor = origin.copy();
        cursor.translate(dir, 4);
        mausoleumWall(worldEditor, rand, levelSettings, cursor, dir);
      }

      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      pillar(worldEditor, levelSettings, cursor);

      start = origin.copy();
      start.translate(dir, 8);
      start.translate(Direction.UP, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, stair, true, false);
    }

    return this;
  }

  private void sarcophagus(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {

    ThemeBase theme = settings.getTheme();

    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(Direction.DOWN);
    start.translate(dir, 5);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = origin.copy();
    cursor.translate(dir, 5);
    cursor.translate(Direction.UP, 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      start = origin.copy();
      start.translate(Direction.DOWN);
      start.translate(dir);
      start.translate(o, 3);
      end = start.copy();
      end.translate(dir, 4);
      end.translate(Direction.UP, 4);
      RectSolid.newRect(start, end).fill(editor, walls);

      cursor = origin.copy();
      cursor.translate(Direction.DOWN);
      cursor.translate(dir, 5);
      cursor.translate(o, 2);
      pillar(editor, settings, cursor);

      start = origin.copy();
      start.translate(Direction.UP, 3);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir, 3);
      stair.setUpsideDown(true).setFacing(o.reverse());
      RectSolid.newRect(start, end).fill(editor, stair);
    }

    cursor = origin.copy();
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Direction.DOWN, 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(dir);
    walls.stroke(editor, cursor);
    cursor.translate(dir);
    walls.stroke(editor, cursor);
    cursor.translate(dir);
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Direction.UP);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(Direction.UP);
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(Direction.DOWN);
      cursor.translate(o);
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 3);
      stair.setUpsideDown(false).setFacing(o);
      RectSolid.newRect(start, end).fill(editor, stair);
      start.translate(Direction.UP);
      end.translate(Direction.UP);
      stair.setUpsideDown(true).setFacing(o);
      RectSolid.newRect(start, end).fill(editor, stair);
      start.translate(Direction.UP);
      end.translate(Direction.UP);
      stair.setUpsideDown(false).setFacing(o);
      RectSolid.newRect(start, end).fill(editor, stair);
    }

  }

  private void crypt(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {

    ThemeBase theme = settings.getTheme();

    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(Direction.DOWN);
    start.translate(dir.antiClockwise());
    end = origin.copy();
    end.translate(Direction.UP, 3);
    end.translate(dir.clockwise());
    end.translate(dir, 3);

    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = origin.copy();
    cursor.translate(dir.reverse());
    cursor.translate(Direction.UP, 2);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(Direction.UP);
    walls.stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir.reverse());
      cursor.translate(Direction.UP);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      walls.stroke(editor, cursor);
      cursor.translate(Direction.UP);
      walls.stroke(editor, cursor);

      start = origin.copy();
      start.translate(Direction.UP, 3);
      start.translate(dir.reverse(), 2);
      start.translate(o, 2);
      end = start.copy();
      end.translate(dir, 7);
      stair.setUpsideDown(true).setFacing(o);
      RectSolid.newRect(start, end).fill(editor, stair, true, false);
    }

    start = origin.copy();
    start.translate(Direction.UP, 3);
    start.translate(dir.reverse(), 2);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.reverse());
    RectSolid.newRect(start, end).fill(editor, stair);

    tomb(editor, rand, settings, origin, dir);
  }

  private void mausoleumWall(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {

    ThemeBase theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    end.translate(dir, 4);
    end.translate(Direction.UP, 4);
    RectSolid.newRect(start, end).fill(editor, walls);

    cursor = origin.copy();
    cursor.translate(Direction.UP);
    tomb(editor, rand, settings, cursor, dir);

    cursor.translate(Direction.UP, 2);
    tomb(editor, rand, settings, cursor, dir);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(Direction.UP);
      cursor.translate(o, 2);
      tomb(editor, rand, settings, cursor, dir);

      cursor.translate(Direction.UP, 2);
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

    start = origin.copy();
    end = origin.copy();
    end.translate(Direction.UP, 4);
    RectSolid.newRect(start, end).fill(editor, walls);

    for (Direction dir : Direction.CARDINAL) {
      cursor = end.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir);
      stair.stroke(editor, cursor, true, false);
    }
  }

  private void tomb(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Direction dir) {

    ThemeBase theme = settings.getTheme();
    Coord cursor;

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush tombStone = BlockType.QUARTZ.getBrush();

    cursor = origin.copy();
    cursor.translate(dir, 2);
    cursor.translate(Direction.UP);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

    cursor = origin.copy();
    cursor.translate(dir, 2);
    RectSolid.newRect(origin, cursor).fill(editor, SingleBlockBrush.AIR);

    if (rand.nextInt(4) == 0) {
      return;
    }

    cursor = origin.copy();
    tombStone.stroke(editor, cursor);

    if (rand.nextInt(5) != 0) {
      return;
    }

    cursor.translate(dir);
    generateSpawner(cursor, MobType.UNDEAD_MOBS);

    cursor.translate(dir);
    editor.getTreasureChestEditor().createChest(cursor, false, settings.getDifficulty(cursor), getRoomSetting().getChestType().orElse(ChestType.chooseRandomAmong(rand, ChestType.COMMON_TREASURES)));
  }

  public int getSize() {
    return 10;
  }
}
