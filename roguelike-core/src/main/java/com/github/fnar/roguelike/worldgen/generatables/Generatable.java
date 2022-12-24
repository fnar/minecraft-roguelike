package com.github.fnar.roguelike.worldgen.generatables;

import greymerk.roguelike.worldgen.Coord;

public interface Generatable {

  Generatable generate(Coord at);

}
