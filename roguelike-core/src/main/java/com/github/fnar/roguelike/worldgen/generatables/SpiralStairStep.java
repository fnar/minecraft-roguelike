package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SpiralStairStep extends BaseGeneratable {

  private final WorldEditor worldEditor;

  private int height = 1;

  protected SpiralStairStep(WorldEditor worldEditor) {
    super(worldEditor);
    this.worldEditor = worldEditor;
  }

  public static SpiralStairStep newStairSteps(WorldEditor worldEditor) {
    return new SpiralStairStep(worldEditor);
  }

  @Override
  public SpiralStairStep generate(Coord at) {
    Coord layer = at.copy();
    for (int i = 0; i < height; i++) {
      SingleBlockBrush.AIR.fill(worldEditor, layer.newRect(2));

      // place the pillar
      pillar.stroke(worldEditor, layer);

      // place the spiral steps
      Direction dir = Direction.CARDINAL.get(layer.getY() % 4);
      Coord step = layer.copy();
      step.translate(dir);
      stairs.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(worldEditor, step);
      step.translate(dir.clockwise());
      stairs.setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, step);
      step.translate(dir.reverse());
      stairs.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, step);

      layer.up();
    }
    return this;
  }

  public SpiralStairStep withHeight(int height) {
    this.height = height;
    return this;
  }
}
