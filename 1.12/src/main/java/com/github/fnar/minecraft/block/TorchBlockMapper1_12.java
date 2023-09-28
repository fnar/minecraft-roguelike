package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TorchBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.worldgen.Direction;

public class TorchBlockMapper1_12 {
   public static IBlockState map(TorchBlock torchBlock) {
    Block minecraftTorchBlock = !torchBlock.isLit()
        ? Blocks.UNLIT_REDSTONE_TORCH
        : torchBlock.isRedstone()
            ? Blocks.REDSTONE_TORCH
            : Blocks.TORCH;

    Direction dir = torchBlock.getFacing();

    EnumFacing facing = dir == Direction.UP
        ? EnumFacing.UP
        : dir == Direction.DOWN
            ? EnumFacing.DOWN
            : DirectionMapper1_12.map(dir.reverse());

    return minecraftTorchBlock.getDefaultState()
        .withProperty(BlockTorch.FACING, facing);
  }
}
