package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.material.Crop;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentWheat extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    Coord cursor = origin.copy();
    cursor.down();
    cursor.translate(dir, 3);
    BlockType.WATER_FLOWING.getBrush().stroke(editor, cursor);

    Direction[] orthogonals = dir.orthogonals();
    Coord start = origin.copy();
    start.translate(dir, 2);
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    start.up(2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, getSecondaryWall(theme));

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.up(1);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
    start.down();
    end.down(2);

    BlockType.FARMLAND.getBrush().fill(editor, RectSolid.newRect(start, end));
    start.up(1);
    end.up(1);
    BlockJumble crops = new BlockJumble();
    crops.addBlock(Crop.WHEAT.getBrush());
    crops.addBlock(Crop.CARROTS.getBrush());
    crops.addBlock(Crop.POTATOES.getBrush());
    RectSolid.newRect(start, end).fill(editor, crops);

    cursor = origin.copy();
    cursor.translate(dir, 3);
    cursor.up(1);
    getSecondaryLightBlock(theme).setFacing(dir.reverse()).stroke(editor, cursor);

    StairsBlock stair = getSecondaryStairs(theme);

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      cursor.up(1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }
  }
}
