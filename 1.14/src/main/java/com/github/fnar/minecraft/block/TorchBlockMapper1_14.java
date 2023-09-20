package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TorchBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;

public class TorchBlockMapper1_14 {
  public static BlockState map(TorchBlock torchBlock) {
    if (torchBlock.isRedstone()) {
      return Blocks.REDSTONE_TORCH
          .getDefaultState()
          .with(RedstoneTorchBlock.LIT, torchBlock.isLit());
    }
    return Blocks.TORCH.getDefaultState();
  }

}
