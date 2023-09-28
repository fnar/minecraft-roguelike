package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.Quartz;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.material.Wood;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class AvidyaRoom extends BaseRoom {

  public AvidyaRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 9;
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
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    int x = at.getX();
    int y = at.getY();
    int z = at.getZ();

    BlockBrush redClay = stainedHardenedClay().setColor(DyeColor.RED);
    BlockBrush whiteClay = stainedHardenedClay().setColor(DyeColor.WHITE);
    BlockBrush pillarQuartz = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

    // clear space
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 8, y, z - 8), new Coord(x + 8, y + 5, z + 8)));

    // roof
    redClay.fill(worldEditor, RectSolid.newRect(new Coord(x - 6, y + 6, z - 6), new Coord(x + 6, y + 6, z + 6)));
    primaryLightBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 6, z + 3)));

    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 7, y - 1, z - 7), new Coord(x + 7, y - 1, z + 7)));


    // floor
    BlockBrush ying = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush yang = stainedHardenedClay().setColor(DyeColor.WHITE);

    // ying
    ying.fill(worldEditor, RectSolid.newRect(new Coord(x - 8, y - 2, z - 8), new Coord(x + 8, y - 2, z + 8)));

    // yang
    BlockBrush quartz = Quartz.SMOOTH.getBrush();
    Coord start = new Coord(x, y, z);
    start.down(2);
    start.west(5);
    Coord end = start.copy();
    start.north(2);
    end.south(2);
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east();
    end.east();
    start.north(2);
    end.south(2);
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east();
    end.east();
    end.north(3);
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east();
    end.east();
    start.north();
    end.north();
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east();
    end.east(3);
    end.north();
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east(3);
    end.east();
    start.south();
    end.north();
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.west(3);
    end.west(2);
    end.north();
    yang.fill(worldEditor, RectSolid.newRect(start, end));

    start.east();
    end.east();
    start.south(7);
    end.south(7);
    yang.fill(worldEditor, RectSolid.newRect(start, end));


    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {

        // upper trim
        start = new Coord(x, y, z);
        start.translate(dir, 8);
        start.up(4);
        end = start.copy();
        end.translate(orthogonals, 8);
        whiteClay.fill(worldEditor, RectSolid.newRect(start, end));
        start.down(5);
        end.down(5);
        BlockType.STONE_BRICKS.getBrush().fill(worldEditor, RectSolid.newRect(start, end));

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.up(5);
        end = start.copy();
        end.translate(orthogonals, 7);
        whiteClay.fill(worldEditor, RectSolid.newRect(start, end));

        // ceiling details
        start = new Coord(x, y, z);
        start.translate(dir, 4);
        start.up(5);
        end = start.copy();
        end.translate(orthogonals, 2);
        quartz.fill(worldEditor, RectSolid.newRect(start, end));

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
        start.down();
        start.translate(dir, 8);
        start.translate(orthogonals, 2);
        end = start.copy();
        end.up(4);
        pillarQuartz.fill(worldEditor, RectSolid.newRect(start, end));
        start.translate(orthogonals, 4);
        end.translate(orthogonals, 4);
        pillarQuartz.fill(worldEditor, RectSolid.newRect(start, end));

        // pillar tops
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 2);
        cursor.up(3);
        Coord cursor2 = cursor.copy();
        pillarTop(worldEditor, cursor);
        cursor2.translate(orthogonals, 4);
        pillarTop(worldEditor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.up(1);
        quartz.stroke(worldEditor, cursor2);
        cursor2.translate(dir.reverse(), 1);
        cursor2.up(1);
        whiteClay.stroke(worldEditor, cursor2);
        cursor.translate(dir.reverse(), 1);
        cursor.up(1);
        pillarTop(worldEditor, cursor);
        cursor.translate(dir.reverse(), 1);
        cursor.up(1);
        pillarTop(worldEditor, cursor);

        // outer wall shell
        start = new Coord(x, y, z);
        start.translate(dir, 9);
        end = start.copy();
        end.translate(orthogonals, 9);
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, whiteClay, false, true);

        // floor outer step circle
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.down();
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
        cursor.down();
        cursor.translate(dir, 8);
        cursor.translate(orthogonals, 3);
        BlockType.GRASS_BLOCK.getBrush().stroke(worldEditor, cursor);
        BlockBrush leaves = Wood.OAK.getLeaves();

        leaves.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS_BLOCK.getBrush().stroke(worldEditor, cursor);
        leaves.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.GRASS_BLOCK.getBrush().stroke(worldEditor, cursor);
        leaves.stroke(worldEditor, cursor);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(orthogonals.reverse(), 1);
        primaryLightBrush().stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 2);
        SingleBlockBrush.AIR.stroke(worldEditor, cursor);
        cursor.down();
        primaryLightBrush().stroke(worldEditor, cursor);
        cursor.up(1);
        cursor.translate(dir.reverse(), 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(dir, 1);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(dir, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.up(1);
        BlockType.COBBLESTONE.getBrush().stroke(worldEditor, cursor);
        cursor.up(3);
        BlockType.WATER_FLOWING.getBrush().stroke(worldEditor, cursor);
      }
    }

    return this;
  }

}
