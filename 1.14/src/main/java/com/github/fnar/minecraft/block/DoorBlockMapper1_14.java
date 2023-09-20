package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.redstone.DoorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;

public class DoorBlockMapper1_14 {

  public static BlockState map(DoorBlock block) {
    JsonElement json = block.getJson();
    BlockState doorMetaBlock = json != null
        ? BlockParser1_14.parse(json)
        : getBlockForDoor(block.getBlockType()).getDefaultState();
    return doorMetaBlock
        .with(net.minecraft.block.DoorBlock.HALF, block.isTop() ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)
        .with(net.minecraft.block.DoorBlock.FACING, DirectionMapper1_14.map(block.getFacing()))
        .with(net.minecraft.block.DoorBlock.OPEN, block.isOpen())
        .with(net.minecraft.block.DoorBlock.HINGE, block.isHingeLeft() ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT);
  }

  private static Block getBlockForDoor(BlockType type) {
    switch (type) {
      case IRON_DOOR:
        return Blocks.IRON_DOOR;
      case BIRCH_DOOR:
        return Blocks.BIRCH_DOOR;
      case SPRUCE_DOOR:
        return Blocks.SPRUCE_DOOR;
      case JUNGLE_DOOR:
        return Blocks.JUNGLE_DOOR;
      case ACACIA_DOOR:
        return Blocks.ACACIA_DOOR;
      case DARK_OAK_DOOR:
        return Blocks.DARK_OAK_DOOR;
      case OAK_DOOR:
      default:
        return Blocks.OAK_DOOR;
    }
  }
}
