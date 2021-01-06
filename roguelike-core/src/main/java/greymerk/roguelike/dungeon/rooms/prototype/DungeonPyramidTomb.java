package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.UNDEAD_MOBS;

public class DungeonPyramidTomb extends DungeonBase {

  public DungeonPyramidTomb(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {


    ThemeBase theme = levelSettings.getTheme();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush blocks = theme.getPrimary().getWall();


    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();

    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Cardinal.UP, 3);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Cardinal.UP, 5);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Cardinal.UP, 7);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    // outer walls
    start = origin.copy();
    end = origin.copy();
    start.translate(Cardinal.NORTH, 7);
    start.translate(Cardinal.WEST, 7);
    end.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.EAST, 7);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 3);
    RectHollow.newRect(start, end).fill(worldEditor, blocks, false, true);

    // floor
    start = origin.copy();
    start.translate(Cardinal.DOWN);
    end = start.copy();
    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getFloor());

    // pillars

    for (Cardinal dir : Cardinal.DIRECTIONS) {


      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(Cardinal.UP, 3);
      ceilingTiles(worldEditor, theme, 9, dir.reverse(), cursor);

      start = origin.copy();
      start.translate(dir, 5);
      start.translate(dir.antiClockwise(), 5);
      end = start.copy();
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      for (Cardinal o : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 5);
        start.translate(o);
        end = start.copy();
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

        start.translate(o, 2);
        end = start.copy();
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);
      }
    }

    // ceiling top
    start = origin.copy();
    start.translate(Cardinal.UP, 8);
    end = start.copy();
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    RectSolid.newRect(start, end).fill(worldEditor, blocks);

    sarcophagus(worldEditor, entrances.get(0), origin);

    return this;
  }

  private void ceilingTiles(WorldEditor editor, ThemeBase theme, int width, Cardinal dir, Coord origin) {

    if (width < 1) {
      return;
    }

    Coord cursor;

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(dir.antiClockwise(), width / 2);
    end.translate(dir.clockwise(), width / 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    for (Cardinal o : dir.orthogonals()) {
      for (int i = 0; i <= width / 2; ++i) {
        if ((width / 2) % 2 == 0) {
          cursor = origin.copy();
          cursor.translate(o, i);
          if (i % 2 == 0) {
            tile(editor, theme, dir, cursor);
          }
        } else {
          cursor = origin.copy();
          cursor.translate(o, i);
          if (i % 2 == 1) {
            tile(editor, theme, dir, cursor);
          }
        }
      }
    }

    cursor = origin.copy();
    cursor.translate(dir);
    cursor.translate(Cardinal.UP);
    ceilingTiles(editor, theme, (width - 2), dir, cursor);
  }

  private void tile(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {
    StairsBlock stair = theme.getPrimary().getStair();
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, origin);
    Coord cursor = origin.copy();
    cursor.translate(Cardinal.UP);
    theme.getPrimary().getPillar().stroke(editor, cursor);
  }


  private void sarcophagus(WorldEditor editor, Cardinal dir, Coord origin) {
    StairsBlock stair = StairsBlock.quartz();
    BlockBrush blocks = BlockType.QUARTZ.getBrush();

    Coord cursor = origin.copy();

    blocks.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    editor.getTreasureChestEditor().createChest(Dungeon.getLevel(cursor.getY()), cursor, false, ChestType.ORE);
    cursor.translate(Cardinal.UP);
    blocks.stroke(editor, cursor);

    for (Cardinal end : dir.orthogonals()) {

      cursor = origin.copy();
      cursor.translate(end);
      blocks.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      generateSpawner(cursor, UNDEAD_MOBS);
      cursor.translate(Cardinal.UP);
      blocks.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(end, 2);
      stair.setUpsideDown(false).setFacing(end).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(end).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(end).stroke(editor, cursor);

      for (Cardinal side : end.orthogonals()) {

        cursor = origin.copy();
        cursor.translate(side);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);

        cursor = origin.copy();
        cursor.translate(side);
        cursor.translate(end);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);

        cursor = origin.copy();
        cursor.translate(side);
        cursor.translate(end, 2);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 8;
  }


}
