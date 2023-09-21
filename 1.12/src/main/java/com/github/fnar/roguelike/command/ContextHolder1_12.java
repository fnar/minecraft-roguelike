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
      int arg1 = getArgAsInt(argumentParser, 1);
      int arg2 = getArgAsInt(argumentParser, 2);

      if (!argumentParser.hasEntry(3)) {
        return new Coord(arg1, 0, arg2);
      } else {
        int arg3 = getArgAsInt(argumentParser, 3);
        return new Coord(arg1, arg2, arg3);
      }
    }
    // todo: internationalization
    throw new IllegalArgumentException(String.format("Could not map arguments to coord: %s.", argumentParser));
  }

  private static int getArgAsInt(ArgumentParser argumentParser, int index) {
    return parseInteger(argumentParser.get(index));
  }

  private static int parseInteger(String input) {
    try {
      return CommandBase.parseInt(input);
    } catch (NumberInvalidException e) {
      throw new RuntimeException("Could not parse input as number: " + input);
    }
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
