package com.github.srwaggon.minecraft.block.decorative;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class FlowerPotBlock extends SingleBlockBrush {

  private Plant content;

  public FlowerPotBlock() {
    super(BlockType.FLOWER_POT);
  }

  public FlowerPotBlock setContent(Plant content) {
    this.content = content;
    return this;
  }

  public Plant getContent() {
    return content;
  }

  public FlowerPotBlock withRandomContent(Random random) {
    return setContent(Plant.chooseRandom(random));
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    boolean succeeded = super.stroke(editor, pos, fillAir, replaceSolid);
    if (succeeded && this.content != null) {
      editor.setFlowerPotContent(pos, this.getContent());
    }
    return succeeded;
  }

  public static FlowerPotBlock flowerPot() {
    return new FlowerPotBlock();
  }

}
