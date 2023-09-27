package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext1_14;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ConfigCommand1_14 {

  public static ArgumentBuilder<CommandSource, ?> configCommand() {
    return Commands.literal("config").executes(ConfigCommand1_14::reloadConfig);
  }

  private static int reloadConfig(CommandContext<CommandSource> context) {
    new ReloadConfigCommand(new CommandContext1_14(context)).run();
    return 0;
  }

}
