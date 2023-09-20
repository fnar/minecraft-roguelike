package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.material.Stone;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class InfestedBlockMapper1_12 {
   public static IBlockState map(InfestedBlock infestedBlock) {
    IBlockState block = Blocks.MONSTER_EGG.getDefaultState();
     Stone stone = infestedBlock.getStone();
     switch (stone) {
      case STONE:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE);
      case COBBLESTONE:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.COBBLESTONE);
      case STONE_BRICKS:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONEBRICK);
      case MOSSY_STONE_BRICKS:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.MOSSY_STONEBRICK);
      case CRACKED_STONE_BRICKS:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CRACKED_STONEBRICK);
      case CHISELED_STONE_BRICKS:
        return block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CHISELED_STONEBRICK);
       default:
         throw new IllegalStateException("Unexpected value: " + stone);
     }
  }
}
