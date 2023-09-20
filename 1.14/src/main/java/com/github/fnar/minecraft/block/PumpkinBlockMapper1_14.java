package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.PumpkinBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;

public class PumpkinBlockMapper1_14 {
  static BlockState map(PumpkinBlock block) {
    if (!block.isCarved()) {
      return Blocks.PUMPKIN.getDefaultState();
    }
    if (!block.isLit()) {
      return Blocks.CARVED_PUMPKIN
          .getDefaultState()
          .with(CarvedPumpkinBlock.FACING, DirectionMapper1_14.map(block.getFacing()));
    }
    return Blocks.JACK_O_LANTERN.getDefaultState()
        .with(CarvedPumpkinBlock.FACING, DirectionMapper1_14.map(block.getFacing()));
  }
}
