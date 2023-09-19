package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.redstone.DoorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class DoorBlockMapper1_12 {

  public static IBlockState map(DoorBlock block) {
    JsonElement json = block.getJson();
    IBlockState doorMetaBlock = json != null
        ? BlockParser1_12.parse(json)
        : getBlockForDoor(block.getBlockType()).getDefaultState();
    return doorMetaBlock
        .withProperty(BlockDoor.HALF, block.isTop() ? BlockDoor.EnumDoorHalf.UPPER : BlockDoor.EnumDoorHalf.LOWER)
        .withProperty(BlockDoor.FACING, FacingMapper1_12.getFacing(block.getFacing()))
        .withProperty(BlockDoor.OPEN, block.isOpen())
        .withProperty(BlockDoor.HINGE, block.isHingeLeft() ? BlockDoor.EnumHingePosition.LEFT : BlockDoor.EnumHingePosition.RIGHT);
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
