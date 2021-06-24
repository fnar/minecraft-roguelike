package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WalledDoorway implements Generatable {

  private final WorldEditor worldEditor;
  private final ThemeBase themeBase;

  public WalledDoorway(WorldEditor worldEditor, ThemeBase themeBase) {
    this.worldEditor = worldEditor;
    this.themeBase = themeBase;
  }

  public void generate(Coord origin, Direction facing) {
    RectSolid rect = RectSolid.newRect(
        origin.copy().translate(facing.left()),
        origin.copy().translate(facing.right()).up(2)
    );
    themeBase.getPrimary().getWall().fill(worldEditor, rect);
  }
}
