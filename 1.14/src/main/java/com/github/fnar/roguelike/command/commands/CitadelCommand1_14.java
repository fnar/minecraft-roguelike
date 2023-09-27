package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext1_14;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;

import greymerk.roguelike.worldgen.Coord;

public class CitadelCommand1_14 {

  public static final String ARG_COORD = "coord";

  public static ArgumentBuilder<CommandSource, ?> citadelCommand() {
    return Commands.literal("citadal")
        .then(Commands.literal("here").executes(CitadelCommand1_14::generateCitadel))
        .then(Commands.literal("nearby").executes(CitadelCommand1_14::generateCitadel))
        .then(Commands.argument(ARG_COORD, BlockPosArgument.blockPos()).executes(CitadelCommand1_14::generateCitadel))
        .executes(CitadelCommand1_14::generateCitadel);
  }

  private static int generateCitadel(CommandContext<CommandSource> context) {
    CommandContext1_14 context1_14 = new CommandContext1_14(context);
    Coord coord = context1_14.getArgumentAsCoord(ARG_COORD).orElse(null);
    new GenerateCitadelCommand(context1_14, coord).run();
    return 0;
  }

}
