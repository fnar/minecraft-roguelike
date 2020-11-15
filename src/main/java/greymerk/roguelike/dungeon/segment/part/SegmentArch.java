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

public class SegmentArch extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(dir.reverse(), true);

    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor = new Coord(origin);
    cursor.translate(dir, 2);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.set(editor, cursor);

    for (Cardinal orth : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(orth, 1);
      cursor.translate(dir, 2);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getPrimary().getWall().set(editor, rand, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.set(editor, cursor);
    }
  }
}
