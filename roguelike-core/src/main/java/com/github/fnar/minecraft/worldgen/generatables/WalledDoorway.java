package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WalledDoorway implements Generatable {

  public void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    RectSolid rect = RectSolid.newRect(
        origin.copy().translate(facing.left(), 2),
        origin.copy().translate(facing.right(), 2).up(2)
    );

    BlockSet blockSet = worldEditor.getRandom().nextBoolean()
        ? levelSettings.getTheme().getPrimary()
        : levelSettings.getTheme().getSecondary();

    blockSet.getWall().fill(worldEditor, rect);
  }
}
