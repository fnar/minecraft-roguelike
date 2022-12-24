package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SpiralStairStep extends BaseGeneratable {

  private final WorldEditor worldEditor;
  private final StairsBlock stair;
  private final BlockBrush pillar;
  private int height = 1;

  public SpiralStairStep(WorldEditor worldEditor, StairsBlock stair, BlockBrush pillar) {
    super(worldEditor);
    this.worldEditor = worldEditor;
    this.stair = stair;
    this.pillar = pillar;
  }

  public SpiralStairStep generate(Coord at) {
    Coord layer = at.copy();
    for (int i = 0; i < height; i++) {
      // clear the layer
      Coord start = layer.copy().north().west();
      Coord end = layer.copy().south().east().up(height);
      RectSolid airRect = RectSolid.newRect(start, end);
      SingleBlockBrush.AIR.fill(worldEditor, airRect);

      // place the pillar
      pillar.stroke(worldEditor, layer);

      // place the spiral steps
      Direction dir = Direction.CARDINAL.get(layer.getY() % 4);
      Coord step = layer.copy();
      step.translate(dir);
      stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(worldEditor, step);
      step.translate(dir.clockwise());
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, step);
      step.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, step);

      layer.up();
    }
    return this;
  }

  public SpiralStairStep withHeight(int height) {
    this.height = height;
    return this;
  }
}
