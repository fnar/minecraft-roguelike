package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.world.BlockPosMapper1_14;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

import greymerk.roguelike.worldgen.Coord;

public class ContextHolder1_14 implements ContextHolder {

  private final com.mojang.brigadier.context.CommandContext<CommandSource> context;
  private final CommandSender1_14 commandSender;

  public ContextHolder1_14(com.mojang.brigadier.context.CommandContext<CommandSource> context) {
    this.context = context;
    this.commandSender = new CommandSender1_14(context.getSource());
  }

  @Override
  public Optional<String> getArgument(int argumentIndex) {
    throw getException1_14DoesNotSupportIndexedArguments();
  }

  @Override
  public Optional<String> getArgument(String argumentName) {
    try {
      return Optional.ofNullable(context.getArgument(argumentName, String.class));
    } catch (IllegalArgumentException ignored) {
    }
    return Optional.empty();
  }

  @Override
  public Optional<Coord> getArgumentAsCoord(String argumentName) {
    try {
      BlockPos position = BlockPosArgument.getBlockPos(context, argumentName);
      return Optional.of(BlockPosMapper1_14.map(position));
    } catch (CommandSyntaxException | IllegalArgumentException ignored) {
    }
    return Optional.empty();
  }

  @Override
  public Optional<Coord> getArgumentAsCoord(int argumentIndex) {
    throw getException1_14DoesNotSupportIndexedArguments();
  }

  @Override
  public CommandSender getCommandSender() {
    return commandSender;
  }

  private static UnsupportedOperationException getException1_14DoesNotSupportIndexedArguments() {
    return new UnsupportedOperationException("1.14 does not support fetching arguments by index.");
  }
}
