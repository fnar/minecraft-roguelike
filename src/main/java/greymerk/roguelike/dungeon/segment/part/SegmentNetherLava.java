package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherLava extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair step = theme.getSecondary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);

    Coord start;
    Coord end;
    Coord cursor;


    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    boolean isAir = editor.isAirBlock(cursor);
    IBlockFactory wall = theme.getSecondary().getWall();

    for (Cardinal orth : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(orth, 1);
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.DOWN, 1);
      if (!isAir) {
        RectSolid.fill(editor, rand, start, end, air);
        lava.set(editor, start);
        start.translate(orth.reverse(), 1);
        lava.set(editor, start);
      }

      cursor = new Coord(origin);
      cursor.translate(dir, 2);

      step.setOrientation(orth.reverse(), false);
      cursor.translate(orth, 1);
      step.set(editor, cursor);

      step.setOrientation(orth.reverse(), true);
      cursor.translate(Cardinal.UP, 1);
      step.set(editor, cursor);

      cursor.translate(Cardinal.UP, 1);
      wall.set(editor, rand, cursor);
      cursor.translate(orth.reverse(), 1);
      wall.set(editor, rand, cursor);
    }

  }

}
