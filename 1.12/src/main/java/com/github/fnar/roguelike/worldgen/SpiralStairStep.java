package com.github.fnar.roguelike.worldgen;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.Generatable;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SpiralStairStep implements Generatable {

  private final WorldEditor worldEditor;
  private final Coord origin;
  private final StairsBlock stair;
  private final BlockBrush fill;

  public SpiralStairStep(WorldEditor worldEditor, Coord origin, StairsBlock stair, BlockBrush fill) {
    this.worldEditor = worldEditor;
    this.origin = origin;
    this.stair = stair;
    this.fill = fill;
  }

  public boolean generate() {
    Coord start = origin.copy();
    start.translate(new Coord(-1, 0, -1));
    Coord end = origin.copy();
    end.translate(new Coord(1, 0, 1));

    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
    fill.stroke(worldEditor, origin);

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
}
