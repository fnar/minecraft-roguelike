package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockHopper;
import net.minecraft.init.Blocks;

import java.util.Arrays;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

public class Hopper {

  public static void generate(WorldEditor editor, Cardinal dir, Coord pos) {
    MetaBlock hopper = new MetaBlock(Blocks.HOPPER);
    if (Arrays.asList(Cardinal.DIRECTIONS).contains(dir)) {
      hopper.withProperty(BlockHopper.FACING, dir.reverse().getFacing());
    }
    hopper.set(editor, pos);
  }
}
