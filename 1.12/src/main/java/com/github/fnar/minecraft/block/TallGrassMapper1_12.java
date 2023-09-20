package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TallPlant;
import com.github.fnar.minecraft.block.decorative.TallPlantBlock;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

class TallGrassMapper1_12 {

  public static IBlockState getTallPlant(TallPlantBlock tallPlantBlock) {
    return Blocks.DOUBLE_PLANT.getDefaultState()
        .withProperty(BlockDoublePlant.VARIANT, getTallPlantMinecraftBlock(tallPlantBlock.getTallPlant()))
        .withProperty(BlockDoublePlant.FACING, DirectionMapper1_12.map(tallPlantBlock.getFacing()))
        .withProperty(BlockDoublePlant.HALF, tallPlantBlock.isTop()
            ? BlockDoublePlant.EnumBlockHalf.UPPER
            : BlockDoublePlant.EnumBlockHalf.LOWER);
  }

  private static BlockDoublePlant.EnumPlantType getTallPlantMinecraftBlock(TallPlant type) {
    switch (type) {
      default:
      case DOUBLE_TALL_GRASS:
        return BlockDoublePlant.EnumPlantType.GRASS;
      case SUNFLOWER:
        return BlockDoublePlant.EnumPlantType.SUNFLOWER;
      case LILAC:
        return BlockDoublePlant.EnumPlantType.SYRINGA;
      case LARGE_FERN:
        return BlockDoublePlant.EnumPlantType.FERN;
      case ROSE_BUSH:
        return BlockDoublePlant.EnumPlantType.ROSE;
      case PEONY:
        return BlockDoublePlant.EnumPlantType.PAEONIA;
    }
  }
}
