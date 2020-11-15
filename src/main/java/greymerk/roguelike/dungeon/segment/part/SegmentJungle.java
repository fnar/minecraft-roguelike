package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentJungle extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();

    MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = wallDirection.orthogonal();
    start = new Coord(origin);
    start.translate(wallDirection, 2);
    end = new Coord(start);
    start.translate(orth[0], 1);
    end.translate(orth[1], 1);
    end.translate(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
    start.translate(Cardinal.DOWN, 1);
    end.translate(Cardinal.DOWN, 2);

    if (rand.nextInt(5) == 0) {
      RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.WATER_FLOWING));
    } else {
      RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.GRASS));
      start.translate(Cardinal.UP, 1);
      end.translate(Cardinal.UP, 1);
      if (rand.nextBoolean()) {
        RectSolid.fill(editor, rand, start, end, leaves);
      }
    }

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.translate(wallDirection, 2);
      cursor.translate(d, 1);
      cursor.translate(Cardinal.UP, 1);
      stair.setOrientation(d.reverse(), true);
      stair.set(editor, cursor);
    }

  }
}
