package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFireArch extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();

    Coord start;
    Coord end;
    Coord cursor;

    Cardinal[] orths = dir.orthogonal();

    start = new Coord(origin);
    start.translate(dir, 3);
    end = new Coord(start);
    start.translate(orths[0]);
    end.translate(orths[0]);
    end.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, walls);
    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    stair.setOrientation(dir.reverse(), false).set(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);
    cursor.translate(Cardinal.DOWN, 2);
    cursor.translate(dir);
    BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.get(BlockType.FIRE).set(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.get(BlockType.IRON_BAR).set(editor, cursor);

    for (Cardinal orth : orths) {

      cursor = new Coord(origin);
      cursor.translate(dir);
      cursor.translate(orth);
      cursor.translate(Cardinal.UP, 2);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    }
  }
}
