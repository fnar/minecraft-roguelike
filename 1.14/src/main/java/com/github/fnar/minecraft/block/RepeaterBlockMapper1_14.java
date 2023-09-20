package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.RepeaterBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;

public class RepeaterBlockMapper1_14 {
  public static BlockState map(RepeaterBlock repeaterBlock) {
    return Blocks.REPEATER.getDefaultState()
        .with(net.minecraft.block.RepeaterBlock.DELAY, repeaterBlock.getDelay().asInt())
        .with(HorizontalBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(repeaterBlock.getFacing()));
  }
}
