package com.github.srwaggon.roguelike.worldgen.block.normal;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;

public class InfestedBlock extends SingleBlockBrush {

  private Stone stone = Stone.STONE;

  public InfestedBlock() {
    super(BlockType.INFESTED_BLOCK);
  }

  public InfestedBlock setStone(Stone stone) {
    this.stone = stone;
    return this;
  }

  public Stone getStone() {
    return stone;
  }

  public static BlockBrush getJumble() {
    Stone[] silverfishBlocks = new Stone[]{
        Stone.COBBLE,
        Stone.STONEBRICK,
        Stone.STONEBRICK_MOSSY,
        Stone.STONEBRICK_CRACKED
    };
    BlockJumble jumble = new BlockJumble();
    for (Stone stone : silverfishBlocks) {
      jumble.addBlock(InfestedBlock.infestedBlock().setStone(stone));
    }
    return jumble;
  }

  public static InfestedBlock infestedBlock() {
    return new InfestedBlock();
  }
}
