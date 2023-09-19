package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.RepeaterBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class RepeaterBlockMapper1_12 {
   public static IBlockState map(RepeaterBlock block) {
    Block block1 = block.isPowered() ? Blocks.POWERED_REPEATER : Blocks.UNPOWERED_REPEATER;
    return block1.getDefaultState()
        .withProperty(BlockRedstoneRepeater.DELAY, block.getDelay().asInt())
        .withProperty(BlockRedstoneRepeater.FACING, FacingMapper1_12.getFacing(block.getFacing()));
  }
}
