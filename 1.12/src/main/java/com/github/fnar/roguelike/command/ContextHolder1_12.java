package com.github.fnar.roguelike.command;

import net.minecraft.command.ICommandSender;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
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
  public Optional<String> getArgument(int argumentIndex) {
    return Optional.ofNullable(argumentParser.get(argumentIndex));
  }

  @Override
  public Optional<String> getArgument(String argumentName) {
    throw getException1_12DoesNotSupportNamedArguments();
  }

  @Override
  public Optional<Coord> getArgumentAsCoord(int argumentIndex) {
    return Optional.of(argumentParser.getCoord(argumentIndex));
  }

  @Override
  public Optional<Coord> getArgumentAsCoord(String argumentName) {
    throw getException1_12DoesNotSupportNamedArguments();
  }

  @Override
  public Optional<Coord> getArgumentAsXZCoord(int argumentIndex) {
    return Optional.of(argumentParser.getXZCoord(argumentIndex));
  }

  @Override
  public CommandSender getCommandSender() {
    return sender;
  }

  @Override
  public Optional<SettingIdentifier> getArgumentAsSettingIdentifier(String argumentName) {
    throw getException1_12DoesNotSupportNamedArguments();
  }

  private static UnsupportedOperationException getException1_12DoesNotSupportNamedArguments() {
    return new UnsupportedOperationException("1.12 does not support fetching arguments by name.");
  }

}
