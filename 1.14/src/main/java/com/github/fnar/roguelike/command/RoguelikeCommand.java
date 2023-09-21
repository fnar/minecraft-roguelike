package com.github.fnar.roguelike.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RoguelikeCommand {

  public static final int CREATIVE_PERMISSION_LEVEL = 3;

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommand::isCreativePlayer)
        .then(biomeCommand());
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(CREATIVE_PERMISSION_LEVEL);
  }

  static RequiredArgumentBuilder<CommandSource, String> biomeCommand() {
    return Commands.argument("biome", StringArgumentType.string())
        .executes(BiomeCommand.biomeCommand())
        .then(BiomeAtLocationCommand.biomeAtLocationCommand());
  }

}
