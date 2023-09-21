package com.github.fnar.roguelike.command.exception;

import greymerk.roguelike.worldgen.Coord;

public class NoValidLocationException extends Exception {
  public NoValidLocationException(Coord coord) {
    super("No valid dungeon for location: " + coord.toString());
  }
}
