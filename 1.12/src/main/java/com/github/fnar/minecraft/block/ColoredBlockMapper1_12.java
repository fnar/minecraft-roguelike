package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.normal.ColoredBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

import greymerk.roguelike.util.DyeColor;

public class ColoredBlockMapper1_12 {
   public static IBlockState getColoredBlock(ColoredBlock coloredBlock) {
    if (coloredBlock.getBlockType() == BlockType.TERRACOTTA) {
      return getTerracottaByColor(coloredBlock.getColor()).getDefaultState();
    }
    return getBlock(coloredBlock.getBlockType()).getDefaultState()
        .withProperty(BlockColored.COLOR, toEnumDyeColor(coloredBlock.getColor()));
  }

  private static Block getBlock(BlockType type) {
    switch (type) {
      case STAINED_HARDENED_CLAY:
        return Blocks.STAINED_HARDENED_CLAY;
      case CARPET:
        return Blocks.CARPET;
      case STAINED_GLASS:
        return Blocks.STAINED_GLASS;
      case STAINED_GLASS_PANE:
        return Blocks.STAINED_GLASS_PANE;
      case CONCRETE:
        return Blocks.CONCRETE;
      case CONCRETE_POWDER:
        return Blocks.CONCRETE_POWDER;
      case WOOL:
      case TERRACOTTA:
      default:
        return Blocks.WOOL;
    }
  }

  private static Block getTerracottaByColor(DyeColor color) {
    switch (color) {
      case WHITE:
        return Blocks.WHITE_GLAZED_TERRACOTTA;
      case ORANGE:
        return Blocks.ORANGE_GLAZED_TERRACOTTA;
      case MAGENTA:
        return Blocks.MAGENTA_GLAZED_TERRACOTTA;
      case LIGHT_BLUE:
        return Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA;
      case YELLOW:
        return Blocks.YELLOW_GLAZED_TERRACOTTA;
      case LIME:
        return Blocks.LIME_GLAZED_TERRACOTTA;
      case PINK:
        return Blocks.PINK_GLAZED_TERRACOTTA;
      case GRAY:
        return Blocks.GRAY_GLAZED_TERRACOTTA;
      case LIGHT_GRAY:
        return Blocks.SILVER_GLAZED_TERRACOTTA;
      case CYAN:
        return Blocks.CYAN_GLAZED_TERRACOTTA;
      case PURPLE:
        return Blocks.PURPLE_GLAZED_TERRACOTTA;
      case BLUE:
        return Blocks.BLUE_GLAZED_TERRACOTTA;
      case BROWN:
        return Blocks.BROWN_GLAZED_TERRACOTTA;
      case GREEN:
        return Blocks.GREEN_GLAZED_TERRACOTTA;
      case RED:
        return Blocks.RED_GLAZED_TERRACOTTA;
      case BLACK:
      default:
        return Blocks.BLACK_GLAZED_TERRACOTTA;
    }
  }

  public static EnumDyeColor toEnumDyeColor(DyeColor color) {
    try {
      return EnumDyeColor.valueOf(color.toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return EnumDyeColor.WHITE;
    }
  }
}
