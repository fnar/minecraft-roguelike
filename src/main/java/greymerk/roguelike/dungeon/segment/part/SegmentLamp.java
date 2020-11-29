package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentLamp extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = dir.orthogonal();

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 3);
    end = new Coord(start);
    start.translate(dir);
    start.translate(orth[0]);
    end.translate(dir.reverse());
    end.translate(orth[1]);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.translate(dir, 3);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(dir, 2);
    end.translate(Cardinal.UP, 6);
    RectSolid.fill(editor, rand, start, end, wall);
    start = new Coord(end);
    start.translate(Cardinal.DOWN, 2);
    start.translate(dir.reverse(), 6);
    start.translate(orth[0], 2);
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal side : orth) {

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP, 2);
      stair.setOrientation(side.reverse(), true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    overheadLight(editor, rand, theme, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 2);

    Coord lever = new Coord(cursor);
    cursor.translate(dir);
    ColorBlock.get(ColorBlock.CLAY, DyeColor.ORANGE).set(editor, cursor);
    Lever.generate(editor, dir.reverse(), lever, false);
    cursor.translate(dir);
    Torch.generate(editor, Torch.REDSTONE, dir, cursor);
    cursor.translate(Cardinal.UP, 2);
    Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
    cursor.translate(Cardinal.UP, 2);
    start = new Coord(cursor);
    end = new Coord(start);
    end.translate(dir.reverse(), 3);
    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
    RectSolid.fill(editor, rand, start, end, wire);
  }

  private void overheadLight(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();

    Coord cursor;

    BlockType.get(BlockType.AIR).set(editor, origin);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(dir.orthogonal()[0]);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    BlockType.get(BlockType.REDSTONE_LAMP).set(editor, cursor);
  }


}
