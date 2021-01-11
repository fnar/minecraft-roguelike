package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.Crop;
import com.github.srwaggon.minecraft.block.decorative.PumpkinBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentWheat extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    Coord cursor;
    Coord start;
    Coord end;

    cursor = origin.copy();
    cursor.translate(Direction.DOWN);
    cursor.translate(dir, 3);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);

    Direction[] orthogonals = dir.orthogonals();
    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    start.translate(Direction.UP, 2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(Direction.UP, 1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Direction.DOWN, 1);
    end.translate(Direction.DOWN, 2);

    RectSolid.newRect(start, end).fill(editor, BlockType.FARMLAND.getBrush());
    start.translate(Direction.UP, 1);
    end.translate(Direction.UP, 1);
    BlockJumble crops = new BlockJumble();
    crops.addBlock(Crop.WHEAT.getBrush());
    crops.addBlock(Crop.CARROTS.getBrush());
    crops.addBlock(Crop.POTATOES.getBrush());
    RectSolid.newRect(start, end).fill(editor, crops);

    cursor = origin.copy();
    cursor.translate(dir, 3);
    cursor.translate(Direction.UP, 1);
    PumpkinBlock.jackOLantern().setFacing(dir.reverse()).stroke(editor, cursor);

    StairsBlock stair = theme.getSecondary().getStair();

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      cursor.translate(Direction.UP, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }
  }
}
