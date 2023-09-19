package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.redstone.TrapdoorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TrapdoorBlockMapper1_12 {
  static IBlockState map(TrapdoorBlock block) {
    Block block1 = block.getMaterial() == Material.METAL ? Blocks.IRON_DOOR : Blocks.TRAPDOOR;
    return block1.getDefaultState()
        .withProperty(BlockTrapDoor.HALF, block.isFlushWithTop() ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM)
        .withProperty(BlockTrapDoor.OPEN, block.isOpen())
        .withProperty(BlockTrapDoor.FACING, FacingMapper1_12.getFacing(block.getFacing()));
  }
}
