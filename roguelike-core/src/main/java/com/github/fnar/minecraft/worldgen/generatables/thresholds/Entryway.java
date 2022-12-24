package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.worldgen.generatables.Generatable;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class Entryway implements Generatable {

  public void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    RectSolid rect = RectSolid.newRect(
        origin.copy().translate(facing.left()),
        origin.copy().translate(facing.right()).up(2)
    );
    SingleBlockBrush.AIR.fill(worldEditor, rect);
  }
}
