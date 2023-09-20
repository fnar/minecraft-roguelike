package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.ComparatorBlock;

import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ComparatorBlockMapper1_12 {
  static IBlockState map(ComparatorBlock block) {
    return (block.isPowered() ? Blocks.POWERED_COMPARATOR : Blocks.UNPOWERED_COMPARATOR)
        .getDefaultState()
        .withProperty(BlockRedstoneComparator.FACING, DirectionMapper1_12.map(block.getFacing()))
        .withProperty(BlockRedstoneComparator.MODE, block.getMode() == ComparatorBlock.Mode.SUBTRACTION
            ? BlockRedstoneComparator.Mode.SUBTRACT
            : BlockRedstoneComparator.Mode.COMPARE);
  }
}
