package com.github.srwaggon.minecraft.block.normal;

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
