package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentAnkh extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord pos) {
    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();
    DyeColor color = DyeColor.get(rand);
    MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, color);
    MetaBlock back = ColorBlock.get(ColorBlock.CLAY, color);
    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(pos);
    start.translate(dir, 2);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);

    RectSolid.fill(editor, rand, start, end, air);


    for (Cardinal o : orth) {

      cursor = new Coord(pos);
      cursor.translate(dir, 2);
      cursor.translate(o);
      stair.setOrientation(o.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(o.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
    }

    start = new Coord(pos);
    start.translate(dir, 3);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, glass);
    start.translate(dir);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, back);

    cursor = new Coord(pos);
    cursor.translate(dir, 3);
    cursor.translate(Cardinal.DOWN);
    glowstone.set(editor, cursor);
    cursor.translate(Cardinal.UP, 4);
    glowstone.set(editor, cursor);
  }

}
