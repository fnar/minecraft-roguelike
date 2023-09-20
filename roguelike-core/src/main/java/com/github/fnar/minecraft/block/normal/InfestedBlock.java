package com.github.fnar.minecraft.block.normal;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.material.Stone;

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

  // TODO: Move, does not belong here
  public static BlockBrush getJumble() {
    Stone[] silverfishBlocks = new Stone[]{
        Stone.COBBLESTONE,
        Stone.STONE_BRICKS,
        Stone.MOSSY_STONE_BRICKS,
        Stone.CRACKED_STONE_BRICKS
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

  @Override
  public InfestedBlock copy() {
    InfestedBlock copy = new InfestedBlock();
    copy.setFacing(getFacing());
    copy.setStone(stone);
    return copy;
  }
}
