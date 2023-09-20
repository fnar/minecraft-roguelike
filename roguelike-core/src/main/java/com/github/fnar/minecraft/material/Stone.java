package com.github.fnar.minecraft.material;

import com.github.fnar.minecraft.block.normal.InfestedBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public enum Stone {

  STONE,
  COBBLESTONE,
  STONE_BRICKS,
  MOSSY_STONE_BRICKS,
  CRACKED_STONE_BRICKS,
  CHISELED_STONE_BRICKS,
  ;

  public BlockBrush getInfested() {
    return InfestedBlock.infestedBlock().setStone(this);
  }

}
