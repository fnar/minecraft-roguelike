package com.github.fnar.roguelike.worldgen;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SpiralStairStep implements Generatable {

  private final WorldEditor worldEditor;
  private final Coord origin;
  private final StairsBlock stair;
  private final BlockBrush pillar;

  public SpiralStairStep(WorldEditor worldEditor, Coord origin, StairsBlock stair, BlockBrush pillar) {
    this.worldEditor = worldEditor;
    this.origin = origin.copy();
    this.stair = stair;
    this.pillar = pillar;
  }

  public boolean generate() {
    Coord start = origin.copy().north().west();
    Coord end = origin.copy().south().east();

    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
    pillar.stroke(worldEditor, origin);

    Direction dir = Direction.CARDINAL.get(origin.getY() % 4);
    Coord cursor = origin.copy();
    cursor.translate(dir);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(worldEditor, cursor);
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    return true;
  }

  public boolean generate(int height) {
    for (int i = 0; i < height; i++) {
      new SpiralStairStep(this.worldEditor, this.origin.copy().up(i), stair, pillar).generate();
    }
    return true;
  }
}
