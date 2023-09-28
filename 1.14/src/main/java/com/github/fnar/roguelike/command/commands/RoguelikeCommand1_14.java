package com.github.fnar.roguelike.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RoguelikeCommand1_14 {

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommand1_14::isCreativePlayer)
        .then(BiomeCommand1_14.biomeCommand())
        .then(CitadelCommand1_14.citadelCommand())
        .then(ConfigCommand1_14.configCommand())
        .then(DungeonCommand1_14.dungeonCommand())
        .then(GiveCommand1_14.giveCommand())
        .then(SettingsCommand1_14.settingsCommand())
        .then(TowerCommand1_14.towerCommand());
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(3);
  }

}
