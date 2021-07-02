package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentAnkh extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, Theme theme, Coord pos) {
    Coord start;
    Coord end;
    Coord cursor;

    StairsBlock stair = theme.getSecondary().getStair();
    DyeColor color = DyeColor.chooseRandom(rand);
    BlockBrush glass = ColoredBlock.stainedGlass().setColor(color);
    BlockBrush back = ColoredBlock.stainedHardenedClay().setColor(color);
    BlockBrush light = theme.getSecondary().getLightBlock();

    Direction[] orthogonals = dir.orthogonals();

    start = pos.copy();
    start.translate(dir, 2);
    end = start.copy();
    end.up(2);

    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);


    for (Direction o : orthogonals) {

      cursor = pos.copy();
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(false).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.up();
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }

    start = pos.copy();
    start.translate(dir, 3);
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, glass);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, back);

    cursor = pos.copy();
    cursor.translate(dir, 3);
    cursor.down();
    light.stroke(editor, cursor);
    cursor.up(4);
    light.stroke(editor, cursor);
  }

}
