package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.PumpkinBlock;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class PumpkinBlockMapper1_12 {
  static IBlockState map(PumpkinBlock block) {
    return (block.isLit() ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN)
        .getDefaultState()
        .withProperty(BlockPumpkin.FACING, FacingMapper1_12.getFacing(block.getFacing()));
  }
}
