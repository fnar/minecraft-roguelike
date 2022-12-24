package com.github.fnar.minecraft.worldgen.generatables;

import greymerk.roguelike.worldgen.Coord;

public interface Generatable {

  Generatable generate(Coord at);

}
