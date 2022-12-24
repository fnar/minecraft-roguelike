package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.worldgen.generatables.BaseGeneratable;
import com.github.fnar.minecraft.worldgen.generatables.Generatable;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class Entryway extends BaseGeneratable implements Generatable {

  public Entryway(WorldEditor worldEditor) {
    super(worldEditor);
  }

  @Override
  public Entryway generate(Coord at) {
    RectSolid rect = RectSolid.newRect(
        at.copy().translate(facing.left()),
        at.copy().translate(facing.right()).up(2)
    );
    SingleBlockBrush.AIR.fill(worldEditor, rect);
    return this;
  }
}
