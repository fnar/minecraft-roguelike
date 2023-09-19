package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.AnvilBlock;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.config.RogueConfig;

public class AnvilBlockMapper1_12 {

  static IBlockState map(AnvilBlock block) {
    if (!RogueConfig.FURNITURE.getBoolean()) {
      return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
    }
    return Blocks.ANVIL.getDefaultState()
        .withProperty(BlockAnvil.DAMAGE, block.getDamage().ordinal())
        .withProperty(BlockAnvil.FACING, FacingMapper1_12.getFacing(block.getFacing()));
  }
}
