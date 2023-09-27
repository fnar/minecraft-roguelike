package com.github.fnar.roguelike.command;

import com.github.fnar.roguelike.command.commands.BiomeCommand1_14;
import com.github.fnar.roguelike.command.commands.DungeonCommand1_14;
import com.github.fnar.roguelike.command.commands.SettingsCommand1_14;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RoguelikeCommand1_14 {

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommand1_14::isCreativePlayer)
        .then(BiomeCommand1_14.biomeCommand())
        .then(DungeonCommand1_14.dungeonCommand())
        .then(SettingsCommand1_14.settingsCommand());
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(3);
  }

}
