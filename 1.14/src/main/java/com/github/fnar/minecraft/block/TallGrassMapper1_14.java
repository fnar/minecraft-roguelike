package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TallPlant;
import com.github.fnar.minecraft.block.decorative.TallPlantBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;

class TallGrassMapper1_14 {

  public static BlockState map(TallPlantBlock tallPlantBlock) {
    return getTallPlantMinecraftBlock(tallPlantBlock.getTallPlant())
        .getDefaultState()
        .with(DoublePlantBlock.HALF, tallPlantBlock.isTop() ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER);
  }

  private static Block getTallPlantMinecraftBlock(TallPlant type) {
    switch (type) {
      default:
      case DOUBLE_TALL_GRASS:
        return Blocks.TALL_GRASS;
      case SUNFLOWER:
        return Blocks.SUNFLOWER;
      case LILAC:
        return Blocks.LILAC;
      case LARGE_FERN:
        return Blocks.LARGE_FERN;
      case ROSE_BUSH:
        return Blocks.ROSE_BUSH;
      case PEONY:
        return Blocks.PEONY;
    }
  }
}
