package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class FlowerPotBlock extends SingleBlockBrush {

  private PlantType content;

  public FlowerPotBlock() {
    super(BlockType.FLOWER_POT);
  }

  public FlowerPotBlock setContent(PlantType content) {
    this.content = content;
    return this;
  }

  public PlantType getContent() {
    return content;
  }

  public FlowerPotBlock withRandomContent(Random random) {
    return setContent(PlantType.chooseRandom(random));
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    if (!super.stroke(editor, pos, fillAir, replaceSolid)) {
      return false;
    }
    if (this.content == null) {
      return true;
    }
    editor.setFlowerPotContent(pos, this.getContent());
    return true;
  }

  public static FlowerPotBlock flowerPot() {
    return new FlowerPotBlock();
  }

  @Override
  public FlowerPotBlock copy() {
    FlowerPotBlock copy = new FlowerPotBlock();
    copy.setFacing(getFacing());
    copy.setContent(content);
    return copy;
  }
}
