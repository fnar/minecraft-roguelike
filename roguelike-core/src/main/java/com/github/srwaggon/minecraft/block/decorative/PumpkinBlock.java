package com.github.srwaggon.minecraft.block.decorative;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.Material;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

public class PumpkinBlock extends SingleBlockBrush {

  private boolean isCarved;
  private boolean isLit;

  public PumpkinBlock() {
    super(BlockType.PUMPKIN, Material.NONE);
  }

  public static PumpkinBlock pumpkin() {
    return new PumpkinBlock();
  }

  public static PumpkinBlock jackOLantern() {
    return new PumpkinBlock().setLit();
  }

  public PumpkinBlock setCarved() {
    isCarved = true;
    return this;
  }

  public PumpkinBlock setLit() {
    this.isLit = true;
    return this;
  }

  public boolean isCarved() {
    return isCarved;
  }

  public boolean isLit() {
    return isLit;
  }
}
