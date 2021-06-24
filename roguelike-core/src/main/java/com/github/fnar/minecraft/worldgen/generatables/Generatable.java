package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;

public interface Generatable {

  void generate(Coord origin, Direction facing);
}
