package com.github.fnar.minecraft.block.redstone;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;

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
