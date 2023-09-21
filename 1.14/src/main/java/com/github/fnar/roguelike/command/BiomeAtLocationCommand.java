package com.github.fnar.roguelike.command;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ILocationArgument;

import greymerk.roguelike.command.CommandContext;

public class BiomeAtLocationCommand extends BiomeCommand {

  public BiomeAtLocationCommand(CommandContext commandContext) {
    super(commandContext);
  }

  public static RequiredArgumentBuilder<CommandSource, ILocationArgument> biomeAtLocationCommand() {
    return Commands.argument("position", BlockPosArgument.blockPos())
        .executes(context -> new BiomeAtLocationCommand(new CommandContext(new ContextHolder1_14(context))).execute());
  }

  @Override
  public int execute() {
    sendBiomeDetails(commandContext, commandContext.getArgumentAsCoord("position"));
    return 0;
  }

}
