package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.worldgen.generatables.BaseGeneratable;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WalledDoorway extends BaseGeneratable {

  public WalledDoorway(WorldEditor worldEditor) {
    super(worldEditor);
  }

  public WalledDoorway generate(Coord at) {
    RectSolid rect = RectSolid.newRect(
        at.copy().translate(facing.left(), 2),
        at.copy().translate(facing.right(), 2).up(2)
    );

    BlockSet blockSet = worldEditor.getRandom().nextBoolean()
        ? levelSettings.getTheme().getPrimary()
        : levelSettings.getTheme().getSecondary();

    blockSet.getWall().fill(worldEditor, rect);

    return this;
  }
}
