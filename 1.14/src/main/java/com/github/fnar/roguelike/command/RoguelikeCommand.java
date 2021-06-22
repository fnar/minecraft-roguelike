package com.github.fnar.roguelike.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RoguelikeCommand {

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommand::isCreativePlayer)
        .then(BiomeCommand.biomeCommand());
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(3);
  }

}
