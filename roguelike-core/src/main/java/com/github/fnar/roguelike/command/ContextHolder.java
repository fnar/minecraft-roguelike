package com.github.fnar.roguelike.command;

import greymerk.roguelike.worldgen.Coord;

public interface ContextHolder {

  String getArgument(String argumentName);

  Coord getArgumentAsCoord(String argumentName);

  CommandSender getCommandSender();

}
