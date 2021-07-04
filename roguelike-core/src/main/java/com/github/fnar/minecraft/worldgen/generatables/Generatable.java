package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public interface Generatable {

  void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing);

}
