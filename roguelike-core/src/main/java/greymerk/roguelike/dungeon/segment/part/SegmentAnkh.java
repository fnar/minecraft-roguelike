package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentAnkh extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord pos) {
    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = theme.getSecondary().getStair();
    DyeColor color = DyeColor.chooseRandom(rand);
    BlockBrush glass = ColoredBlock.stainedGlass().setColor(color);
    BlockBrush back = ColoredBlock.stainedHardenedClay().setColor(color);
    BlockBrush glowstone = BlockType.GLOWSTONE.getBrush();

    Cardinal[] orthogonals = dir.orthogonals();

    start = new Coord(pos);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);

    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);


    for (Cardinal o : orthogonals) {

      cursor = new Coord(pos);
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    start = new Coord(pos);
    start.translate(dir, 3);
    end = new Coord(start);
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, start, end, glass);
    start.translate(dir);
    end.translate(dir);
    RectSolid.fill(editor, start, end, back);

    cursor = new Coord(pos);
    cursor.translate(dir, 3);
    cursor.translate(Cardinal.DOWN);
    glowstone.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 4);
    glowstone.stroke(editor, cursor);
  }

}
