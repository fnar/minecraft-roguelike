package com.github.srwaggon.roguelike.worldgen;

import greymerk.roguelike.worldgen.Coord;

interface BlockChecker {
  boolean isChest(Coord coord);

  boolean isTrappedChest(Coord coord);

  boolean isMobSpawner(Coord coord);

  boolean isAir(Coord coord);
}
