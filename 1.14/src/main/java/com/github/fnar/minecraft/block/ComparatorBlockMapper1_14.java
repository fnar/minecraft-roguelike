package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.ComparatorBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.ComparatorMode;

public class ComparatorBlockMapper1_14 {
  static BlockState map(ComparatorBlock block) {
    return Blocks.COMPARATOR
        .getDefaultState()
        .with(net.minecraft.block.ComparatorBlock.POWERED, block.isPowered())
        .with(net.minecraft.block.ComparatorBlock.MODE, mapMode(block))
        .with(net.minecraft.block.ComparatorBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(block.getFacing()));
  }

  private static ComparatorMode mapMode(ComparatorBlock block) {
    return block.isInSubtractMode()
        ? ComparatorMode.SUBTRACT
        : ComparatorMode.COMPARE;
  }
}
