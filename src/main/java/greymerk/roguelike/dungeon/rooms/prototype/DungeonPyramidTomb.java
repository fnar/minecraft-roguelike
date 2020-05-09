package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.treasure.Treasure.ORE;
import static greymerk.roguelike.treasure.Treasure.createChest;
import static greymerk.roguelike.worldgen.spawners.MobType.UNDEAD_MOBS;

public class DungeonPyramidTomb extends DungeonBase {

  public DungeonPyramidTomb(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {


    ITheme theme = settings.getTheme();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory blocks = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);


    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.UP, 3);
    start.translate(Cardinal.NORTH, 4);
    start.translate(Cardinal.WEST, 4);
    end.translate(Cardinal.SOUTH, 4);
    end.translate(Cardinal.EAST, 4);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.UP, 5);
    start.translate(Cardinal.NORTH, 3);
    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.SOUTH, 3);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(Cardinal.UP, 7);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    // outer walls
    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(Cardinal.NORTH, 7);
    start.translate(Cardinal.WEST, 7);
    end.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.EAST, 7);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 3);
    RectHollow.fill(editor, rand, start, end, blocks, false, true);

    // floor
    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(Cardinal.NORTH, 6);
    start.translate(Cardinal.WEST, 6);
    end.translate(Cardinal.SOUTH, 6);
    end.translate(Cardinal.EAST, 6);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());

    // pillars

    for (Cardinal dir : Cardinal.directions) {


      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(Cardinal.UP, 3);
      ceilingTiles(editor, rand, theme, 9, dir.reverse(), cursor);

      start = new Coord(origin);
      start.translate(dir, 5);
      start.translate(dir.left(), 5);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal o : dir.orthogonal()) {
        start = new Coord(origin);
        start.translate(dir, 5);
        start.translate(o);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);

        start.translate(o, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
      }
    }

    // ceiling top
    start = new Coord(origin);
    start.translate(Cardinal.UP, 8);
    end = new Coord(start);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.WEST);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.EAST);
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    sarcophagus(editor, rand, settings, entrances[0], origin);

    return this;
  }

  private void ceilingTiles(IWorldEditor editor, Random rand, ITheme theme, int width, Cardinal dir, Coord origin) {

    if (width < 1) {
      return;
    }

    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;

    Coord start = new Coord(origin);
    Coord end = new Coord(origin);
    start.translate(dir.left(), width / 2);
    end.translate(dir.right(), width / 2);
    RectSolid.fill(editor, rand, start, end, air, true, true);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);

    for (Cardinal o : dir.orthogonal()) {
      for (int i = 0; i <= width / 2; ++i) {
        if ((width / 2) % 2 == 0) {
          cursor = new Coord(origin);
          cursor.translate(o, i);
          if (i % 2 == 0) {
            tile(editor, rand, theme, dir, cursor);
          }
        } else {
          cursor = new Coord(origin);
          cursor.translate(o, i);
          if (i % 2 == 1) {
            tile(editor, rand, theme, dir, cursor);
          }
        }
      }
    }

    cursor = new Coord(origin);
    cursor.translate(dir);
    cursor.translate(Cardinal.UP);
    ceilingTiles(editor, rand, theme, (width - 2), dir, cursor);
  }

  private void tile(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IStair stair = theme.getPrimary().getStair();
    stair.setOrientation(dir, true).set(editor, origin);
    Coord cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    theme.getPrimary().getPillar().set(editor, rand, cursor);
  }


  private void sarcophagus(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    IStair stair = new MetaStair(StairType.QUARTZ);
    MetaBlock blocks = BlockType.get(BlockType.QUARTZ);

    Coord cursor;

    cursor = new Coord(origin);
    blocks.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    createChest(editor, rand, Dungeon.getLevel(cursor.getY()), cursor, false, ORE);
    cursor.translate(Cardinal.UP);
    blocks.set(editor, cursor);

    for (Cardinal end : dir.orthogonal()) {

      cursor = new Coord(origin);
      cursor.translate(end);
      blocks.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      SpawnerSettings spawners = settings.getSpawners();
      generateSpawner(editor, rand, cursor, settings.getDifficulty(cursor), spawners, UNDEAD_MOBS);
      cursor.translate(Cardinal.UP);
      blocks.set(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(end, 2);
      stair.setOrientation(end, false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(end, true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(end, false).set(editor, cursor);

      for (Cardinal side : end.orthogonal()) {

        cursor = new Coord(origin);
        cursor.translate(side);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);

        cursor = new Coord(origin);
        cursor.translate(side);
        cursor.translate(end);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);

        cursor = new Coord(origin);
        cursor.translate(side);
        cursor.translate(end, 2);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 8;
  }


}
