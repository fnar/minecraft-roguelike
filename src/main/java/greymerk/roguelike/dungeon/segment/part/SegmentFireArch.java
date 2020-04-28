package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFireArch extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();

    Coord start;
    Coord end;
    Coord cursor;

    Cardinal[] orths = dir.orthogonal();

    start = new Coord(origin);
    start.add(dir, 3);
    end = new Coord(start);
    start.add(orths[0]);
    end.add(orths[0]);
    end.add(Cardinal.UP, 2);
    end.add(dir);
    RectSolid.fill(editor, rand, start, end, walls);
    cursor = new Coord(origin);
    cursor.add(dir, 2);
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);
    cursor.add(Cardinal.UP, 2);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.add(Cardinal.DOWN, 2);
    cursor.add(dir);
    BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.FIRE).set(editor, cursor);
    cursor.add(dir.reverse());
    BlockType.get(BlockType.IRON_BAR).set(editor, cursor);

    for (Cardinal orth : orths) {

      cursor = new Coord(origin);
      cursor.add(dir);
      cursor.add(orth);
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    }
  }
}
