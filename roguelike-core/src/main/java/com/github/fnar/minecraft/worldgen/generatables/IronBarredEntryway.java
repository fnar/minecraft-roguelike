package com.github.fnar.minecraft.worldgen.generatables;

import com.github.fnar.minecraft.block.BlockType;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class IronBarredEntryway implements Generatable{

  private final WorldEditor worldEditor;

  public IronBarredEntryway(WorldEditor worldEditor) {
    this.worldEditor = worldEditor;
  }

  public void generate(Coord origin, Direction facing) {
    RectSolid rect = RectSolid.newRect(
        origin.copy().translate(facing.left()),
        origin.copy().translate(facing.right()).up(2)
    );
    BlockType.IRON_BAR.getBrush().fill(worldEditor, rect);
  }
}
