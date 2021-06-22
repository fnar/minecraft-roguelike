package com.github.fnar.minecraft.worldgen;

import com.github.fnar.minecraft.block.BlockType;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class NetherPortal {

  private final WorldEditor worldEditor;

  public NetherPortal(WorldEditor worldEditor) {
    this.worldEditor = worldEditor;
  }

  public void generate(Coord origin, Direction front, int width, int height) {
    int leftSide = width / 2;
    int rightSide = width - leftSide - 1;
    RectHollow.newRect(
        origin.copy().translate(front.left(), leftSide),
        origin.copy().translate(front.right(), rightSide).up(height - 1)
    ).fill(worldEditor, BlockType.OBSIDIAN.getBrush());

    RectSolid.newRect(
        origin.copy().translate(front.left(), leftSide - 1).up(),
        origin.copy().translate(front.right(), rightSide - 1).up(height - 2)
    ).fill(worldEditor, BlockType.NETHER_PORTAL.getBrush().setFacing(front.clockwise()));
  }
}
