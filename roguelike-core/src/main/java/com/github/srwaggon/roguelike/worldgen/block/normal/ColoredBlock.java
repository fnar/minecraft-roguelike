package com.github.srwaggon.roguelike.worldgen.block.normal;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.Material;

import greymerk.roguelike.util.DyeColor;
import lombok.Getter;

@Getter
public class ColoredBlock extends SingleBlockBrush {

  private DyeColor color = DyeColor.RED;

  public ColoredBlock(BlockType blockType, Material material) {
    super(blockType, material);
  }

  public static ColoredBlock carpet() {
    return new ColoredBlock(BlockType.CARPET, Material.NONE);
  }

  public static ColoredBlock concretePowder() {
    return new ColoredBlock(BlockType.CONCRETE_POWDER, Material.NONE);
  }

  public static ColoredBlock concrete() {
    return new ColoredBlock(BlockType.CONCRETE, Material.NONE);
  }

  public static ColoredBlock stainedGlass() {
    return new ColoredBlock(BlockType.STAINED_GLASS, Material.GLASS);
  }

  public static ColoredBlock stainedGlassPane() {
    return new ColoredBlock(BlockType.STAINED_GLASS_PANE, Material.GLASS);
  }

  public static ColoredBlock stainedHardenedClay() {
    return new ColoredBlock(BlockType.STAINED_HARDENED_CLAY, Material.CLAY);
  }

  public static ColoredBlock terracotta() {
    return new ColoredBlock(BlockType.TERRACOTTA, Material.CLAY);
  }

  public static ColoredBlock wool() {
    return new ColoredBlock(BlockType.WOOL, Material.FUR);
  }

  public ColoredBlock setColor(DyeColor color) {
    this.color = color;
    return this;
  }

}
