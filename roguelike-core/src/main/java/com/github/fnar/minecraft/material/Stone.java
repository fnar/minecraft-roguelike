package com.github.fnar.minecraft.material;

import com.github.fnar.minecraft.block.normal.InfestedBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public enum Stone {

  STONE,
  COBBLE,
  STONEBRICK,
  STONEBRICK_MOSSY,
  STONEBRICK_CRACKED,
  STONEBRICK_CHISELED,
  ;

  public BlockBrush getInfested() {
    return InfestedBlock.infestedBlock().setStone(this);
  }

}
