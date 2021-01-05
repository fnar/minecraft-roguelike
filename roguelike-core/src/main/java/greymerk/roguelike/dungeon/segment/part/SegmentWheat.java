package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Crop;
import com.github.srwaggon.roguelike.worldgen.block.decorative.PumpkinBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentWheat extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir, 3);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);

    Cardinal[] orthogonals = dir.orthogonals();
    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    start.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.fill(editor, start, end, theme.getSecondary().getWall());

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.translate(Cardinal.UP, 1);
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    start.translate(Cardinal.DOWN, 1);
    end.translate(Cardinal.DOWN, 2);

    RectSolid.fill(editor, start, end, BlockType.FARMLAND.getBrush());
    start.translate(Cardinal.UP, 1);
    end.translate(Cardinal.UP, 1);
    BlockJumble crops = new BlockJumble();
    crops.addBlock(Crop.WHEAT.getBrush());
    crops.addBlock(Crop.CARROTS.getBrush());
    crops.addBlock(Crop.POTATOES.getBrush());
    RectSolid.fill(editor, start, end, crops);

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    cursor.translate(Cardinal.UP, 1);
    PumpkinBlock.jackOLantern().setFacing(dir.reverse()).stroke(editor, cursor);

    StairsBlock stair = theme.getSecondary().getStair();

    for (Cardinal d : orthogonals) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      cursor.translate(Cardinal.UP, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }
  }
}
