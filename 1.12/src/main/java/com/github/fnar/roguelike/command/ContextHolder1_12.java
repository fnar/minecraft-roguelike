package com.github.fnar.roguelike.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import greymerk.roguelike.command.CommandSender;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;

public class ContextHolder1_12 implements ContextHolder {

  private final CommandSender1_12 sender;
  private final List<String> args;

  public ContextHolder1_12(ICommandSender sender, List<String> args) {
    this.sender = new CommandSender1_12(sender);
    this.args = args;
  }

  @Override
  public Coord getArgumentAsCoord(String argumentName) {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (argumentParser.hasEntry(0)) {
      try {
        int x = CommandBase.parseInt(argumentParser.get(0));
        int y = CommandBase.parseInt(argumentParser.get(1));
        int z = CommandBase.parseInt(argumentParser.get(2));
        return new Coord(x, y, z);
      } catch (NumberInvalidException e) {
        throw new RuntimeException(e);
      }
    }
    // TODO: Fix;
    throw new NotImplementedException("Part of 1.12 commands refactor");
  }

  @Override
  public String getArgument(String argumentName) {
    // TODO: Fix;
    throw new NotImplementedException("Part of 1.12 commands refactor");
  }

  @Override
  public CommandSender getCommandSender() {
    return sender;
  }
}
