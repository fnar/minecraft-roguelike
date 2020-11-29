package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMushrooms extends SegmentBase {

  private BlockWeightedRandom mushrooms;


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal wallDirection, ThemeBase theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    mushrooms = new BlockWeightedRandom();
    mushrooms.addBlock(FlowerPot.getFlower(FlowerPot.REDMUSHROOM), 3);
    mushrooms.addBlock(FlowerPot.getFlower(FlowerPot.BROWNMUSHROOM), 3);
    mushrooms.addBlock(air, 10);

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
    RectSolid.fill(editor, rand, start, end, air);
    start.translate(Cardinal.DOWN, 1);
    end.translate(Cardinal.DOWN, 2);

    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.MYCELIUM));
    start.translate(Cardinal.UP, 1);
    end.translate(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, mushrooms);

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
