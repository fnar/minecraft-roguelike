package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class LadderPillar extends BaseGeneratable {

  private int height = 1;

  protected LadderPillar(WorldEditor worldEditor) {
    super(worldEditor);
    withFacing(Direction.randomCardinal(worldEditor.getRandom()));
  }

  public static LadderPillar newLadderPillar(WorldEditor worldEditor) {
    return new LadderPillar(worldEditor);
  }

  @Override
  public BaseGeneratable generate(Coord at) {
    Coord layer = at.copy();

    for (int i = 0; i < height; i++) {
      BlockBrush layerFill = i == height - 1 ? pillar : SingleBlockBrush.AIR;
      layerFill.fill(worldEditor, layer.newRect(2));

      pillar.stroke(worldEditor, layer);

      Coord ladder = layer.copy().translate(facing);
      BlockType.LADDER.getBrush().setFacing(facing.reverse()).stroke(worldEditor, ladder);

      layer.up();
    }

    return this;
  }

  public LadderPillar withHeight(int height) {
    this.height = height;
    return this;
  }

}
