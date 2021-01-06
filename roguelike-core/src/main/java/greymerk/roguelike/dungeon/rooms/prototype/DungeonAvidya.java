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
import greymerk.roguelike.worldgen.Cardinal;
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
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      stair.setUpsideDown(true).setFacing(dir);
      cursor.translate(dir, 1);
      stair.stroke(editor, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    BlockBrush redClay = stainedHardenedClay().setColor(DyeColor.RED);
    BlockBrush whiteClay = stainedHardenedClay().setColor(DyeColor.WHITE);
    BlockBrush pillarQuartz = Quartz.PILLAR.getBrush().setFacing(Cardinal.UP);
    BlockBrush glowstone = BlockType.GLOWSTONE.getBrush();

    // clear space
    RectSolid.newRect(new Coord(x - 8, y, z - 8), new Coord(x + 8, y + 5, z + 8)).fill(editor, SingleBlockBrush.AIR);

    // roof
    RectSolid.newRect(new Coord(x - 6, y + 6, z - 6), new Coord(x + 6, y + 6, z + 6)).fill(editor, redClay);
    RectSolid.newRect(new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(editor, glowstone);

    RectSolid.newRect(new Coord(x - 7, y - 1, z - 7), new Coord(x + 7, y - 1, z + 7)).fill(editor, SingleBlockBrush.AIR);


    // floor
    BlockBrush ying = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush yang = stainedHardenedClay().setColor(DyeColor.WHITE);

    // ying
    RectSolid.newRect(new Coord(x - 8, y - 2, z - 8), new Coord(x + 8, y - 2, z + 8)).fill(editor, ying);

    // yang
    BlockBrush quartz = Quartz.SMOOTH.getBrush();
    Coord start = new Coord(x, y, z);
    start.translate(Cardinal.DOWN, 2);
    start.translate(Cardinal.WEST, 5);
    Coord end = new Coord(start);
    start.translate(Cardinal.NORTH, 2);
    end.translate(Cardinal.SOUTH, 2);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.NORTH, 2);
    end.translate(Cardinal.SOUTH, 2);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.NORTH, 3);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.NORTH, 1);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 3);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.SOUTH, 1);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.WEST, 3);
    end.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.NORTH, 1);
    RectSolid.newRect(start, end).fill(editor, yang);

    start.translate(Cardinal.EAST, 1);
    end.translate(Cardinal.EAST, 1);
    start.translate(Cardinal.SOUTH, 7);
    end.translate(Cardinal.SOUTH, 7);
    RectSolid.newRect(start, end).fill(editor, yang);


    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {

        // upper trim
        start = new Coord(x, y, z);
        start.translate(dir, 8);
        start.translate(Cardinal.UP, 4);
        end = new Coord(start);
        end.translate(orthogonals, 8);
        RectSolid.newRect(start, end).fill(editor, whiteClay);
        start.translate(Cardinal.DOWN, 5);
        end.translate(Cardinal.DOWN, 5);
        RectSolid.newRect(start, end).fill(editor, BlockType.STONE_BRICK.getBrush());

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 5);
        end = new Coord(start);
        end.translate(orthogonals, 7);
        RectSolid.newRect(start, end).fill(editor, whiteClay);

        // ceiling details
        start = new Coord(x, y, z);
        start.translate(dir, 4);
        start.translate(Cardinal.UP, 5);
        end = new Coord(start);
        end.translate(orthogonals, 2);
        RectSolid.newRect(start, end).fill(editor, quartz);

        Coord cursor = new Coord(end);
        cursor.translate(dir, 1);
        quartz.stroke(editor, cursor);
        cursor = new Coord(end);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(orthogonals, 1);
        quartz.stroke(editor, cursor);
        pillarTop(editor, cursor);

        // pillars
        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 8);
        start.translate(orthogonals, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 4);
        RectSolid.newRect(start, end).fill(editor, pillarQuartz);
        start.translate(orthogonals, 4);
        end.translate(orthogonals, 4);
        RectSolid.newRect(start, end).fill(editor, pillarQuartz);

        // pillar tops
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 2);
        cursor.translate(Cardinal.UP, 3);
        Coord cursor2 = new Coord(cursor);
        pillarTop(editor, cursor);
        cursor2.translate(orthogonals, 4);
        pillarTop(editor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Cardinal.UP, 1);
        quartz.stroke(editor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.translate(Cardinal.UP, 1);
        whiteClay.stroke(editor, cursor2);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        pillarTop(editor, cursor);
        cursor.translate(dir.reverse(), 1);
        cursor.translate(Cardinal.UP, 1);
        pillarTop(editor, cursor);

        // outer wall shell
        start = new Coord(x, y, z);
        start.translate(dir, 9);
        end = new Coord(start);
        end.translate(orthogonals, 9);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, whiteClay, false, true);

        // floor outer step circle
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(Cardinal.DOWN, 1);
        StairsBlock stair = StairsBlock.stoneBrick();
        stair.setUpsideDown(false).setFacing(dir.reverse());
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        stair.setUpsideDown(false).setFacing(orthogonals.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        cursor.translate(dir.reverse(), 1);
        stair.stroke(editor, cursor);

        stair.setUpsideDown(false).setFacing(dir.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        stair.setUpsideDown(false).setFacing(orthogonals.reverse());
        cursor.translate(orthogonals, 1);
        stair.stroke(editor, cursor);

        cursor.translate(dir.reverse(), 1);
        stair.stroke(editor, cursor);

        // perimeter decor
        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.DOWN, 1);
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 3);
        BlockType.GRASS.getBrush().stroke(editor, cursor);
        BlockBrush leaves = Wood.OAK.getLeaves();

        leaves.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS.getBrush().stroke(editor, cursor);
        leaves.stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS.getBrush().stroke(editor, cursor);
        leaves.stroke(editor, cursor);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(orthogonals.reverse(), 1);
        glowstone.stroke(editor, cursor);
        cursor.translate(orthogonals, 2);
        SingleBlockBrush.AIR.stroke(editor, cursor);
        cursor.translate(Cardinal.DOWN, 1);
        glowstone.stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(dir, 1);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(dir, 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        BlockType.COBBLESTONE.getBrush().stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 3);
        BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);
      }
    }

    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
