package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.world.BlockPosMapper1_14;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;

import greymerk.roguelike.worldgen.Coord;

class ContextHolder1_14 implements ContextHolder {
  private final com.mojang.brigadier.context.CommandContext<CommandSource> context;
  private final CommandSender1_14 commandSender;

  public ContextHolder1_14(com.mojang.brigadier.context.CommandContext<CommandSource> context) {
    this.context = context;
    this.commandSender = new CommandSender1_14(context.getSource());
  }

  @Override
  public String getArgument(String argumentName) {
    return context.getArgument(argumentName, String.class);
  }

  @Override
  public Coord getArgumentAsCoord(String argumentName) {
    try {
      return BlockPosMapper1_14.map(BlockPosArgument.getLoadedBlockPos(context, "position"));
    } catch (CommandSyntaxException e) {
      // todo: Create new module-neutral Exception Class
      throw new RuntimeException(e);
    }
  }

  @Override
  public CommandSender getCommandSender() {
    return commandSender;
  }
}
