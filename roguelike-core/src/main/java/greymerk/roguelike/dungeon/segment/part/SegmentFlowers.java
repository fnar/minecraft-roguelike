package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.decorative.FlowerPotBlock;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFlowers extends SegmentShelf {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {
    super.genWall(editor, level, dir, theme, origin);

    Direction[] orthogonals = dir.orthogonals();

    Coord start = origin.copy();
    start.translate(dir, 2);
    start.up();
    Coord end = start.copy();
    start.translate(orthogonals[0]);
    end.translate(orthogonals[1]);
    for (Coord c : new RectSolid(start, end)) {
      if (editor.getRandom().nextInt(3) == 0 && editor.isSolidBlock(c)) {
        FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, c);
      }
    }
  }
}
