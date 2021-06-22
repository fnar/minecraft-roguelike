package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.FlowerPotBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFlowers extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor = origin.copy();
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    cursor.translate(dir, 2);
    start = cursor.copy();
    start.translate(orthogonals[0], 1);
    end = cursor.copy();
    end.translate(orthogonals[1], 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);
    start.translate(dir, 1);
    end.translate(dir, 1);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall(), false, true);
    start.translate(dir.reverse(), 1);
    start.up(1);
    end.translate(dir.reverse(), 1);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR, false, true);
    cursor.up(2);
    for (Direction d : orthogonals) {
      Coord c = cursor.copy();
      c.translate(d, 1);
      stair.setUpsideDown(true).setFacing(d.reverse());
      stair.stroke(editor, c);
    }

    start = origin.copy();
    start.translate(dir, 2);
    start.up();
    end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    for (Coord c : new RectSolid(start, end)) {
      if (rand.nextInt(3) == 0 && editor.isSolidBlock(c)) {
        FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom(c)).stroke(editor, c);
      }
    }
  }
}
