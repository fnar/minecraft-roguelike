package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.BedBlock;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class BedBlockMapper1_12 {
  static IBlockState map(BedBlock bedBlock) {
    return Blocks.BED.getDefaultState()
        .withProperty(BlockBed.FACING, FacingMapper1_12.getFacing(bedBlock.getFacing()))
        .withProperty(BlockBed.PART, bedBlock.isHead() ? BlockBed.EnumPartType.HEAD : BlockBed.EnumPartType.FOOT);
  }
}
