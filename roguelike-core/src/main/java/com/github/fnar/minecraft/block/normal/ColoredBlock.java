package com.github.fnar.minecraft.block.normal;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.util.DyeColor;
import lombok.Getter;

@Getter
public class ColoredBlock extends SingleBlockBrush {

  private DyeColor color = DyeColor.chooseRandom(new Random());

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

  public ColoredBlock black() {
    return this.setColor(DyeColor.BLACK);
  }

  public ColoredBlock blue() {
    return this.setColor(DyeColor.BLUE);
  }

  public ColoredBlock green() {
    return this.setColor(DyeColor.GREEN);
  }

  public ColoredBlock orange() {
    return this.setColor(DyeColor.ORANGE);
  }

  public ColoredBlock purple() {
    return this.setColor(DyeColor.PURPLE);
  }

  public ColoredBlock red() {
    return this.setColor(DyeColor.RED);
  }

  public ColoredBlock yellow() {
    return this.setColor(DyeColor.YELLOW);
  }

  public ColoredBlock white() {
    return this.setColor(DyeColor.WHITE);
  }

  @Override
  public ColoredBlock copy() {
    ColoredBlock copy = new ColoredBlock(getBlockType(), getMaterial());
    copy.setFacing(getFacing());
    copy.setColor(color);
    return copy;
  }
}
