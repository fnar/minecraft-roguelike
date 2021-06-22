package com.github.fnar.minecraft.block.normal;

import com.github.fnar.minecraft.block.BlockType;

import greymerk.roguelike.worldgen.BlockBrush;

public enum Quartz {

  SMOOTH(BlockType.SMOOTH_QUARTZ),
  CHISELED(BlockType.CHISELED_QUARTZ),
  PILLAR(BlockType.PILLAR_QUARTZ),
  ;

  private final BlockType blockType;

  Quartz(BlockType blockType) {
    this.blockType = blockType;
  }

  public BlockBrush getBrush() {
    return this.blockType.getBrush();
  }

}
