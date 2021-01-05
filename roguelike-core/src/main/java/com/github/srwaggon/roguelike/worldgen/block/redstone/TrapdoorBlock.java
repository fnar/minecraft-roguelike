package com.github.srwaggon.roguelike.worldgen.block.redstone;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.Material;

public class TrapdoorBlock extends SingleBlockBrush {

  private boolean isFlushWithTop;
  private boolean isOpen;

  public TrapdoorBlock(Material material) {
    super(BlockType.TRAPDOOR, material);
  }

  public static TrapdoorBlock wood() {
    return new TrapdoorBlock(Material.WOOD);
  }

  public static TrapdoorBlock iron() {
    return new TrapdoorBlock(Material.METAL);
  }

  public TrapdoorBlock setFlushWithTop() {
    isFlushWithTop = true;
    return this;
  }

  public boolean isFlushWithTop() {
    return isFlushWithTop;
  }

  public TrapdoorBlock setOpen() {
    isOpen = true;
    return this;
  }

  public boolean isOpen() {
    return isOpen;
  }
}
