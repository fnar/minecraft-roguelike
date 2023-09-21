package com.github.fnar.roguelike.command;

import net.minecraft.command.ICommandSender;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;

public class ContextHolder1_12 implements ContextHolder {

  private final CommandSender1_12 sender;
  private final ArgumentParser argumentParser;

  public ContextHolder1_12(ICommandSender sender, List<String> args) {
    this.sender = new CommandSender1_12(sender);
    this.argumentParser = new ArgumentParser(args);
  }

  @Override
  public Coord getArgumentAsCoord(String argumentName) {
    return argumentParser.getCoord(1);
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
