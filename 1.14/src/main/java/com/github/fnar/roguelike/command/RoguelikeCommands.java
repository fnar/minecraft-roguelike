package com.github.fnar.roguelike.command;

import com.github.fnar.roguelike.command.commands.BiomeAtLocationCommand;
import com.github.fnar.roguelike.command.commands.BiomeAtPlayerCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ILocationArgument;

public class RoguelikeCommands {

  public static final int CREATIVE_PERMISSION_LEVEL = 3;

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommands::isCreativePlayer)
        .then(biomeCommand());
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(CREATIVE_PERMISSION_LEVEL);
  }

  static RequiredArgumentBuilder<CommandSource, String> biomeCommand() {
    return Commands.argument("biome", StringArgumentType.string())
        .executes(biomeAtPlayerCommand())
        .then(biomeAtLocationCommand());
  }

  public static Command<CommandSource> biomeAtPlayerCommand() {
    return context -> {
      new BiomeAtPlayerCommand(new CommandContext(new ContextHolder1_14(context))).run();
      return 0;
    };
  }

  public static RequiredArgumentBuilder<CommandSource, ILocationArgument> biomeAtLocationCommand() {
    return Commands.argument("position", BlockPosArgument.blockPos())
        .executes(context -> {
          new BiomeAtLocationCommand(new CommandContext(new ContextHolder1_14(context))).run();
          return 0;
        });
  }
}
