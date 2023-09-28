package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TorchBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;

public class TorchBlockMapper1_14 {

  public static BlockState map(TorchBlock torchBlock) {
    return withFacing(torchBlock, withLitStatus(torchBlock, getBlockState(torchBlock)));
  }

  private static BlockState withLitStatus(TorchBlock torchBlock, BlockState blockState) {
    return torchBlock.isRedstone()
        ? blockState.with(RedstoneTorchBlock.LIT, torchBlock.isLit())
        : blockState;
  }

  private static BlockState withFacing(TorchBlock torchBlock, BlockState blockState) {
    return torchBlock.isAttachedToWall()
        ? blockState.with(HorizontalBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(torchBlock.getFacing().reverse()))
        : blockState;
  }

  private static BlockState getBlockState(TorchBlock torchBlock) {
    return torchBlock.isRedstone()
        ? torchBlock.isAttachedToWall()
        ? Blocks.REDSTONE_WALL_TORCH.getDefaultState()
        : Blocks.REDSTONE_TORCH.getDefaultState()
        : torchBlock.isAttachedToWall()
            ? Blocks.WALL_TORCH.getDefaultState()
            : Blocks.TORCH.getDefaultState();
  }

}
