package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

public class Piston {

  public static void generate(WorldEditor editor, Coord origin, Cardinal dir, boolean sticky) {

    MetaBlock piston = new MetaBlock(sticky ? Blocks.STICKY_PISTON : Blocks.PISTON);
    piston.withProperty(BlockPistonBase.FACING, dir.reverse().getFacing());
    piston.set(editor, origin);
  }

}
