package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.ContextHolder1_14;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;

import greymerk.roguelike.worldgen.Coord;

public class BiomeCommand1_14 {

  public static final String ARG_COORD = "position";

  public static LiteralArgumentBuilder<CommandSource> biomeCommand() {
    return Commands.literal("biome")
        .executes(BiomeCommand1_14::listBiomes)
        .then(Commands.argument(ARG_COORD, BlockPosArgument.blockPos())
            .executes(BiomeCommand1_14::listBiomes));
  }

  private static int listBiomes(CommandContext<CommandSource> context) {
    com.github.fnar.roguelike.command.CommandContext commandContext = new com.github.fnar.roguelike.command.CommandContext(new ContextHolder1_14(context));
    Coord coord = commandContext.getArgumentAsCoord(ARG_COORD).orElse(null);
    new BiomeCommand(commandContext, coord).run();
    return 0;
  }

}
