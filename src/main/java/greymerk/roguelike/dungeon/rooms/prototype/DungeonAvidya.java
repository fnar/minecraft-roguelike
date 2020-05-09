package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonAvidya extends DungeonBase {

  public DungeonAvidya(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void pillarTop(IWorldEditor editor, Random rand, Coord cursor) {
    IStair step = new MetaStair(StairType.QUARTZ);
    for (Cardinal dir : Cardinal.directions) {
      step.setOrientation(dir, true);
      cursor.translate(dir, 1);
      step.set(editor, rand, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    MetaBlock redClay = ColorBlock.get(ColorBlock.CLAY, DyeColor.RED);
    MetaBlock whiteClay = ColorBlock.get(ColorBlock.CLAY, DyeColor.WHITE);
    MetaBlock pillarQuartz = Quartz.getPillar(Cardinal.UP);
    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
    MetaBlock air = BlockType.get(BlockType.AIR);

    // clear space
    RectSolid.fill(editor, rand, new Coord(x - 8, y, z - 8), new Coord(x + 8, y + 5, z + 8), air);

    // roof
    RectSolid.fill(editor, rand, new Coord(x - 6, y + 6, z - 6), new Coord(x + 6, y + 6, z + 6), redClay, true, true);
    RectSolid.fill(editor, rand, new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 6, z + 3), glowstone);

    RectSolid.fill(editor, rand, new Coord(x - 7, y - 1, z - 7), new Coord(x + 7, y - 1, z + 7), air);


    // floor
    MetaBlock ying = ColorBlock.get(ColorBlock.CLAY, DyeColor.BLACK);
    MetaBlock yang = ColorBlock.get(ColorBlock.CLAY, DyeColor.WHITE);

    // ying
    RectSolid.fill(editor, rand, new Coord(x - 8, y - 2, z - 8), new Coord(x + 8, y - 2, z + 8), ying);

    // yang
    MetaBlock quartz = Quartz.get(Quartz.SMOOTH);
    Coord start = new Coord(x, y, z);
    start.translate(Cardinal.DOWN, 2);
    start.translate(Cardinal.WEST, 5);
    Coord end = new Coord(start);
    start.translate(Cardinal.NORTH, 2);
    end.translate(Cardinal.SOUTH, 2);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.NORTH, 2);
    end.translate(Cardinal.SOUTH, 2);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.NORTH, 3);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.NORTH, 1);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.SOUTH, 1);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.fill(editor, rand, start, end, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.SOUTH, 7);
    RectSolid.fill(editor, rand, start, end, yang);


    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {

        // upper trim
        start = new Coord(x, y, z);
        start.translate(dir, 8);
        start.translate(Cardinal.UP, 4);
        end = new Coord(start);
        end.translate(orth, 8);
        RectSolid.fill(editor, rand, start, end, whiteClay);
        start.translate(Cardinal.DOWN, 5);
        end.translate(Cardinal.DOWN, 5);
        RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.STONE_BRICK));

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 5);
        end = new Coord(start);
        end.translate(orth, 7);
        RectSolid.fill(editor, rand, start, end, whiteClay);

        // ceiling details
        start = new Coord(x, y, z);
        start.translate(dir, 4);
        start.translate(Cardinal.UP, 5);
        end = new Coord(start);
        end.translate(orth, 2);
        RectSolid.fill(editor, rand, start, end, quartz);

        Coord cursor = new Coord(end);
        cursor.translate(dir, 1);
        quartz.set(editor, rand, cursor);
        cursor = new Coord(end);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orth, 1);
        quartz.set(editor, rand, cursor);
        pillarTop(editor, rand, cursor);

        // pillars
        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 8);
        start.translate(orth, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, pillarQuartz);
        start.translate(orth, 4);
        end.translate(orth, 4);
        RectSolid.fill(editor, rand, start, end, pillarQuartz);

        // pillar tops
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(orth, 2);
        cursor.translate(Cardinal.UP, 3);
        Coord cursor2 = new Coord(cursor);
        pillarTop(editor, rand, cursor);
        cursor2.translate(orth, 4);
        pillarTop(editor, rand, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Cardinal.UP, 1);
        quartz.set(editor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Cardinal.UP, 1);
        whiteClay.set(editor, cursor2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        pillarTop(editor, rand, cursor);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        pillarTop(editor, rand, cursor);

        // outer wall shell
        start = new Coord(x, y, z);
        start.translate(dir, 9);
        end = new Coord(start);
        end.translate(orth, 9);
        end.translate(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, whiteClay, false, true);

        // floor outer step circle
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(Cardinal.DOWN, 1);
        IStair step = new MetaStair(StairType.STONEBRICK);
        step.setOrientation(dir.reverse(), false);
        step.set(editor, cursor);

        cursor.translate(orth, 1);
        step.set(editor, cursor);

        cursor.translate(orth, 1);
        step.set(editor, cursor);

        step.setOrientation(orth.reverse(), false);
        cursor.translate(orth, 1);
        step.set(editor, cursor);

        cursor.translate(dir.reverse(), 1);
        step.set(editor, cursor);

        step.setOrientation(dir.reverse(), false);
        cursor.translate(orth, 1);
        step.set(editor, cursor);

        step.setOrientation(orth.reverse(), false);
        cursor.translate(orth, 1);
        step.set(editor, cursor);

        cursor.translate(dir.reverse(), 1);
        step.set(editor, cursor);

        // perimeter decor
        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.DOWN, 1);
        cursor.translate(dir, 8);
        cursor.translate(orth, 3);
        BlockType.get(BlockType.GRASS).set(editor, cursor);
        MetaBlock leaves = Leaves.get(Wood.OAK, false);

        leaves.set(editor, cursor);
        cursor.translate(orth, 1);
        BlockType.get(BlockType.GRASS).set(editor, cursor);
        leaves.set(editor, cursor);
        cursor.translate(orth, 1);
        BlockType.get(BlockType.GRASS).set(editor, cursor);
        leaves.set(editor, cursor);
        cursor.translate(dir.reverse(), 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(orth.reverse(), 1);
        glowstone.set(editor, cursor);
        cursor.translate(orth, 2);
        air.set(editor, cursor);
        cursor.translate(Cardinal.DOWN, 1);
        glowstone.set(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        cursor.translate(dir.reverse(), 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(dir, 1);
        cursor.translate(orth, 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(dir, 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(orth, 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        BlockType.get(BlockType.COBBLESTONE).set(editor, cursor);
        cursor.translate(Cardinal.UP, 3);
        BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);
      }
    }

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }

  public boolean validLocation(IWorldEditor editor, Cardinal dir, int x, int y, int z) {
    for (Coord pos : new RectHollow(new Coord(x - 10, y - 2, z - 10), new Coord(x + 10, y + 5, z + 10))) {
      if (!editor.getBlock(pos).getMaterial().isSolid()) {
        return false;
      }
    }

    return true;
  }

}
