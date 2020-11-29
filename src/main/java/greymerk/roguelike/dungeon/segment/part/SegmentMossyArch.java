package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMossyArch extends SegmentBase {

  private boolean spawnHoleSet = false;

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal wallDirection, ThemeBase theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(wallDirection.reverse(), true);

    MetaBlock air = BlockType.get(BlockType.AIR);

    generateSecret(level.getSettings().getSecrets(), editor, rand, level.getSettings(), wallDirection, new Coord(origin));

    Coord cursor = new Coord(origin);
    cursor.translate(wallDirection, 2);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    stair.set(editor, cursor);

    for (Cardinal orth : wallDirection.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(orth, 1);
      cursor.translate(wallDirection, 2);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.translate(Cardinal.UP, 1);
      theme.getSecondary().getWall().set(editor, rand, cursor);
      cursor.translate(wallDirection.reverse(), 1);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.translate(wallDirection, 2);
    cursor.translate(Cardinal.DOWN, 1);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    cursor.translate(wallDirection, 1);
    BlockType.get(BlockType.VINE).set(editor, cursor);

    if (!spawnHoleSet) {
      RectSolid.fill(editor, rand, new Coord(0, 2, 0).translate(origin), new Coord(0, 5, 0).translate(origin), BlockType.get(BlockType.AIR));
      Vine.fill(editor, new Coord(0, 3, 0).translate(origin), new Coord(0, 5, 0).translate(origin));

      if (!editor.isAirBlock(new Coord(0, 6, 0).translate(origin))) {
        BlockType.get(BlockType.WATER_FLOWING).set(editor, new Coord(0, 7, 0).translate(origin));
      }
      spawnHoleSet = true;
    }
  }

}
