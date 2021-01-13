package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.UNDEAD_MOBS;

public class DungeonPyramidTomb extends DungeonBase {

  public DungeonPyramidTomb(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {


    ThemeBase theme = levelSettings.getTheme();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush blocks = theme.getPrimary().getWall();


    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();

    start.translate(Direction.NORTH, 6);
    start.translate(Direction.WEST, 6);
    end.translate(Direction.SOUTH, 6);
    end.translate(Direction.EAST, 6);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Direction.UP, 3);
    start.translate(Direction.NORTH, 4);
    start.translate(Direction.WEST, 4);
    end.translate(Direction.SOUTH, 4);
    end.translate(Direction.EAST, 4);
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Direction.UP, 5);
    start.translate(Direction.NORTH, 3);
    start.translate(Direction.WEST, 3);
    end.translate(Direction.SOUTH, 3);
    end.translate(Direction.EAST, 3);
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();

    start.translate(Direction.UP, 7);
    start.translate(Direction.NORTH, 2);
    start.translate(Direction.WEST, 2);
    end.translate(Direction.SOUTH, 2);
    end.translate(Direction.EAST, 2);
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    // outer walls
    start = origin.copy();
    end = origin.copy();
    start.translate(Direction.NORTH, 7);
    start.translate(Direction.WEST, 7);
    end.translate(Direction.SOUTH, 7);
    end.translate(Direction.EAST, 7);
    start.translate(Direction.DOWN);
    end.translate(Direction.UP, 3);
    RectHollow.newRect(start, end).fill(worldEditor, blocks, false, true);

    // floor
    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(Direction.NORTH, 6);
    start.translate(Direction.WEST, 6);
    end.translate(Direction.SOUTH, 6);
    end.translate(Direction.EAST, 6);
    RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getFloor());

    // pillars

    for (Direction dir : Direction.CARDINAL) {


      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(Direction.UP, 3);
      ceilingTiles(worldEditor, theme, 9, dir.reverse(), cursor);

      start = origin.copy();
      start.translate(dir, 5);
      start.translate(dir.antiClockwise(), 5);
      end = start.copy();
      end.translate(Direction.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      for (Direction o : dir.orthogonals()) {
        start = origin.copy();
        start.translate(dir, 5);
        start.translate(o);
        end = start.copy();
        end.translate(Direction.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);

        start.translate(o, 2);
        end = start.copy();
        end.translate(Direction.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);
      }
    }

    // ceiling top
    start = origin.copy();
    start.translate(Direction.UP, 8);
    end = start.copy();
    start.translate(Direction.NORTH);
    start.translate(Direction.WEST);
    end.translate(Direction.SOUTH);
    end.translate(Direction.EAST);
    RectSolid.newRect(start, end).fill(worldEditor, blocks);

    sarcophagus(worldEditor, entrances.get(0), origin);

    return this;
  }

  private void ceilingTiles(WorldEditor editor, ThemeBase theme, int width, Direction dir, Coord origin) {

    if (width < 1) {
      return;
    }

    Coord cursor;

    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(dir.antiClockwise(), width / 2);
    end.translate(dir.clockwise(), width / 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Direction.UP);
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    for (Direction o : dir.orthogonals()) {
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
    cursor.translate(Direction.UP);
    ceilingTiles(editor, theme, (width - 2), dir, cursor);
  }

  private void tile(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {
    StairsBlock stair = theme.getPrimary().getStair();
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, origin);
    Coord cursor = origin.copy();
    cursor.translate(Direction.UP);
    theme.getPrimary().getPillar().stroke(editor, cursor);
  }


  private void sarcophagus(WorldEditor editor, Direction dir, Coord origin) {
    StairsBlock stair = StairsBlock.quartz();
    BlockBrush blocks = BlockType.QUARTZ.getBrush();

    Coord cursor = origin.copy();

    blocks.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    editor.getTreasureChestEditor().createChest(cursor, false, Dungeon.getLevel(cursor.getY()), ChestType.ORE);
    cursor.translate(Direction.UP);
    blocks.stroke(editor, cursor);

    for (Direction end : dir.orthogonals()) {

      cursor = origin.copy();
      cursor.translate(end);
      blocks.stroke(editor, cursor);
      cursor.translate(Direction.UP);
      generateSpawner(cursor, UNDEAD_MOBS);
      cursor.translate(Direction.UP);
      blocks.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(end, 2);
      stair.setUpsideDown(false).setFacing(end).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      stair.setUpsideDown(true).setFacing(end).stroke(editor, cursor);
      cursor.translate(Direction.UP);
      stair.setUpsideDown(false).setFacing(end).stroke(editor, cursor);

      for (Direction side : end.orthogonals()) {

        cursor = origin.copy();
        cursor.translate(side);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);

        cursor = origin.copy();
        cursor.translate(side);
        cursor.translate(end);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);

        cursor = origin.copy();
        cursor.translate(side);
        cursor.translate(end, 2);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(true).setFacing(side).stroke(editor, cursor);
        cursor.translate(Direction.UP);
        stair.setUpsideDown(false).setFacing(side).stroke(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 8;
  }


}
