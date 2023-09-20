package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.TrapdoorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.state.properties.Half;

public class TrapdoorBlockMapper1_14 {
  static BlockState map(TrapdoorBlock block) {
    return mapBlock(block)
        .getDefaultState()
        .with(TrapDoorBlock.HALF, block.isFlushWithTop() ? Half.TOP : Half.BOTTOM)
        .with(TrapDoorBlock.OPEN, block.isOpen())
        .with(TrapDoorBlock.HORIZONTAL_FACING, DirectionMapper1_14.map(block.getFacing()));
  }

  private static Block mapBlock(TrapdoorBlock block) {
    return block.getMaterial() == Material.METAL ? Blocks.IRON_DOOR : Blocks.OAK_TRAPDOOR;
  }
}
