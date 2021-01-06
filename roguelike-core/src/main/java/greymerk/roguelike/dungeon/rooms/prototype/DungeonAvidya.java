package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Quartz;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonAvidya extends DungeonBase {

  public DungeonAvidya(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void pillarTop(WorldEditor editor, Coord cursor) {
    StairsBlock stair = StairsBlock.quartz();
    for (Direction dir : Direction.CARDINAL) {
      stair.setUpsideDown(true).setFacing(dir);
      cursor.translate(dir, 1);
      stair.stroke(editor, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    BlockBrush redClay = stainedHardenedClay().setColor(DyeColor.RED);
    BlockBrush whiteClay = stainedHardenedClay().setColor(DyeColor.WHITE);
    BlockBrush pillarQuartz = Quartz.PILLAR.getBrush().setFacing(Direction.UP);
    BlockBrush glowstone = BlockType.GLOWSTONE.getBrush();

    // clear space
    RectSolid.newRect(new Coord(x - 8, y, z - 8), new Coord(x + 8, y + 5, z + 8)).fill(worldEditor, SingleBlockBrush.AIR);

    // roof
    RectSolid.newRect(new Coord(x - 6, y + 6, z - 6), new Coord(x + 6, y + 6, z + 6)).fill(worldEditor, redClay);
    RectSolid.newRect(new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(worldEditor, glowstone);

    RectSolid.newRect(new Coord(x - 7, y - 1, z - 7), new Coord(x + 7, y - 1, z + 7)).fill(worldEditor, SingleBlockBrush.AIR);


    // floor
    BlockBrush ying = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush yang = stainedHardenedClay().setColor(DyeColor.WHITE);

    // ying
    RectSolid.newRect(new Coord(x - 8, y - 2, z - 8), new Coord(x + 8, y - 2, z + 8)).fill(worldEditor, ying);

    // yang
    BlockBrush quartz = Quartz.SMOOTH.getBrush();
    Coord start = new Coord(x, y, z);
    start.translate(Direction.DOWN, 2);
    start.translate(Direction.WEST, 5);
    Coord end = start.copy();
    start.translate(Direction.NORTH, 2);
    end.translate(Direction.SOUTH, 2);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 1);
    end.translate(Direction.EAST, 1);
    start.translate(Direction.NORTH, 2);
    end.translate(Direction.SOUTH, 2);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 1);
    end.translate(Direction.EAST, 1);
    end.translate(Direction.NORTH, 3);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 1);
    end.translate(Direction.EAST, 1);
    start.translate(Direction.NORTH, 1);
    end.translate(Direction.NORTH, 1);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 1);
    end.translate(Direction.EAST, 3);
    end.translate(Direction.NORTH, 1);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 3);
    end.translate(Direction.EAST, 1);
    start.translate(Direction.SOUTH, 1);
    end.translate(Direction.NORTH, 1);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.WEST, 3);
    end.translate(Direction.WEST, 2);
    end.translate(Direction.NORTH, 1);
    RectSolid.newRect(start, end).fill(worldEditor, yang);

    start.translate(Direction.EAST, 1);
    end.translate(Direction.EAST, 1);
    start.translate(Direction.SOUTH, 7);
    end.translate(Direction.SOUTH, 7);
    RectSolid.newRect(start, end).fill(worldEditor, yang);


    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {

        // upper trim
        start = new Coord(x, y, z);
        start.translate(dir, 8);
        start.translate(Direction.UP, 4);
        end = start.copy();
        end.translate(orthogonals, 8);
        RectSolid.newRect(start, end).fill(worldEditor, whiteClay);
        start.translate(Direction.DOWN, 5);
        end.translate(Direction.DOWN, 5);
        RectSolid.newRect(start, end).fill(worldEditor, BlockType.STONE_BRICK.getBrush());

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(Direction.UP, 5);
        end = start.copy();
        end.translate(orthogonals, 7);
        RectSolid.newRect(start, end).fill(worldEditor, whiteClay);

        // ceiling details
        start = new Coord(x, y, z);
        start.translate(dir, 4);
        start.translate(Direction.UP, 5);
        end = start.copy();
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(worldEditor, quartz);

        Coord cursor = end.copy();
        cursor.translate(dir, 1);
        quartz.stroke(worldEditor, cursor);
        cursor = end.copy();
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orthogonals, 1);
        quartz.stroke(worldEditor, cursor);
        pillarTop(worldEditor, cursor);

        // pillars
        start = new Coord(x, y, z);
        start.translate(Direction.DOWN, 1);
        start.translate(dir, 8);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.translate(Direction.UP, 4);
        RectSolid.newRect(start, end).fill(worldEditor, pillarQuartz);
        start.translate(orthogonals, 4);
        end.translate(orthogonals, 4);
        RectSolid.newRect(start, end).fill(worldEditor, pillarQuartz);

        // pillar tops
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 2);
        cursor.translate(Direction.UP, 3);
        Coord cursor2 = cursor.copy();
        pillarTop(worldEditor, cursor);
        cursor2.translate(orthogonals, 4);
        pillarTop(worldEditor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Direction.UP, 1);
        quartz.stroke(worldEditor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Direction.UP, 1);
        whiteClay.stroke(worldEditor, cursor2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Direction.UP, 1);
        pillarTop(worldEditor, cursor);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Direction.UP, 1);
        pillarTop(worldEditor, cursor);

        // outer wall shell
        start = new Coord(x, y, z);
        start.translate(dir, 9);
        end = start.copy();
        end.translate(orthogonals, 9);
        end.translate(Direction.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, whiteClay, false, true);

        // floor outer step circle
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(Direction.DOWN, 1);
        StairsBlock stair = StairsBlock.stoneBrick();
        stair.setUpsideDown(false).setFacing(dir.reverse());
        stair.stroke(worldEditor, cursor);

        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);

        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);

        stair.setUpsideDown(false).setFacing(orthogonals.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);

        cursor.translate(dir.reverse(), 1);
        stair.stroke(worldEditor, cursor);

        stair.setUpsideDown(false).setFacing(dir.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);

        stair.setUpsideDown(false).setFacing(orthogonals.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);

        cursor.translate(dir.reverse(), 1);
        stair.stroke(worldEditor, cursor);

        // perimeter decor
        cursor = new Coord(x, y, z);
        cursor.translate(Direction.DOWN, 1);
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 3);
        BlockType.GRASS.getBrush().stroke(worldEditor, cursor);
        BlockBrush leaves = Wood.OAK.getLeaves();

        leaves.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS.getBrush().stroke(worldEditor, cursor);
        leaves.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS.getBrush().stroke(worldEditor, cursor);
        leaves.stroke(worldEditor, cursor);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(orthogonals.reverse(), 1);
        glowstone.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 2);
        SingleBlockBrush.AIR.stroke(worldEditor, cursor);
        cursor.translate(Direction.DOWN, 1);
        glowstone.stroke(worldEditor, cursor);
        cursor.translate(Direction.UP, 1);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(dir, 1);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(dir, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(Direction.UP, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(Direction.UP, 3);
        BlockType.WATER_FLOWING.getBrush().stroke(worldEditor, cursor);
      }
    }

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
