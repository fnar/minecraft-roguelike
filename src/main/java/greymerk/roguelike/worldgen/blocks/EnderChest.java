package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockEnderChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;


public class EnderChest {
  public static void set(WorldEditor editor, Cardinal dir, Coord pos) {

    EnumFacing facing = Arrays.asList(Cardinal.DIRECTIONS).contains(dir)
        ? dir.reverse().getFacing()
        : Cardinal.SOUTH.getFacing();

    MetaBlock chest = new MetaBlock(Blocks.ENDER_CHEST);
    chest.withProperty(BlockEnderChest.FACING, facing);
    chest.set(editor, pos);
  }
}
