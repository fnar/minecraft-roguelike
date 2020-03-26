package greymerk.roguelike.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.state.IBlockState;

import greymerk.roguelike.worldgen.blocks.StairType;

public class MetaStair extends MetaBlock implements IStair {

  public MetaStair(Block block) {
    super(block);
  }

  public MetaStair(MetaBlock block) {
    super(block);
  }

  public MetaStair(StairType type) {
    super(StairType.getBlock(type));
  }

  public MetaStair setOrientation(Cardinal dir, Boolean upsideDown) {
    IBlockState stair = getBlock().getDefaultState();
    stair = stair.withProperty(BlockStairs.FACING, dir.getFacing());
    stair = stair.withProperty(BlockStairs.HALF, upsideDown ? EnumHalf.TOP : EnumHalf.BOTTOM);
    setState(stair);
    return this;
  }

}
