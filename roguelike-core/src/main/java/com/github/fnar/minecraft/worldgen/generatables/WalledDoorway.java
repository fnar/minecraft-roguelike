package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WalledDoorway implements Generatable {

  private final WorldEditor worldEditor;
  private final Theme theme;

  public WalledDoorway(WorldEditor worldEditor, Theme theme) {
    this.worldEditor = worldEditor;
    this.theme = theme;
  }

  public void generate(Coord origin, Direction facing) {
    RectSolid rect = RectSolid.newRect(
        origin.copy().translate(facing.left()),
        origin.copy().translate(facing.right()).up(2)
    );
    theme.getPrimary().getWall().fill(worldEditor, rect);
  }
}
