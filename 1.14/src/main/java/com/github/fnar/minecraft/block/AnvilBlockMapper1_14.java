package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.AnvilBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import greymerk.roguelike.config.RogueConfig;

public class AnvilBlockMapper1_14 {

  static BlockState map(AnvilBlock block) {
    if (!RogueConfig.FURNITURE.getBoolean()) {
      return Blocks.ANDESITE.getDefaultState();
    }

    BlockState ANVIL = getAnvilState(block);
    if (ANVIL != null) {
      return ANVIL;
    }
    return Blocks.ANVIL.getDefaultState()
        .with(net.minecraft.block.AnvilBlock.FACING, DirectionMapper1_14.map(block.getFacing()));
  }

  private static BlockState getAnvilState(AnvilBlock block) {
    switch (block.getDamage()) {
      case NEW:
        return Blocks.ANVIL.getDefaultState();
      case DAMAGED:
        return Blocks.CHIPPED_ANVIL.getDefaultState();
      case VERY_DAMAGED:
        return Blocks.DAMAGED_ANVIL.getDefaultState();
    }
    return null;
  }
}
