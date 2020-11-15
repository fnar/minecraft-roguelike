package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockVine;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class Vine {

  public static void fill(WorldEditor editor, Coord start, Coord end) {
    for (Coord cursor : new RectSolid(start, end)) {
      set(editor, cursor);
    }
  }

  public static void set(WorldEditor editor, Coord origin) {
    if (!editor.isAirBlock(origin)) {
      return;
    }
    MetaBlock vine = BlockType.get(BlockType.VINE);
    for (Cardinal dir : Cardinal.directions) {
      Coord c = new Coord(origin);
      c.translate(dir);
      if (editor.canPlace(vine, c, dir)) {
        setOrientation(vine, dir).set(editor, c);
        return;
      }
    }
  }

  public static MetaBlock setOrientation(MetaBlock vine, Cardinal dir) {
    vine.withProperty(BlockVine.NORTH, dir == Cardinal.NORTH);
    vine.withProperty(BlockVine.EAST, dir == Cardinal.EAST);
    vine.withProperty(BlockVine.SOUTH, dir == Cardinal.SOUTH);
    vine.withProperty(BlockVine.WEST, dir == Cardinal.WEST);
    return vine;
  }
}
