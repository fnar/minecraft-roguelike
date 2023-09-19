package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.material.Stone;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class InfestedBlockMapper1_12 {
   public static IBlockState map(InfestedBlock block) {
    IBlockState block1 = Blocks.MONSTER_EGG.getDefaultState();
    Stone stone = block.getStone();
    switch (stone) {
      default:
      case STONE:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE);
      case COBBLE:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.COBBLESTONE);
      case STONEBRICK:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONEBRICK);
      case STONEBRICK_MOSSY:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.MOSSY_STONEBRICK);
      case STONEBRICK_CRACKED:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CRACKED_STONEBRICK);
      case STONEBRICK_CHISELED:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CHISELED_STONEBRICK);
    }
  }
}
