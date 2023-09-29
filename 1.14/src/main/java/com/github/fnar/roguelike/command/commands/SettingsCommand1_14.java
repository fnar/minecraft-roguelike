package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext1_14;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class SettingsCommand1_14 {

  public static final String ARG_SETTING_NAME = "setting name";

  public static LiteralArgumentBuilder<CommandSource> settingsCommand() {
    return Commands.literal("settings")
        .executes(SettingsCommand1_14::sendUsage)
        .then(Commands.literal("list")
            .executes(SettingsCommand1_14::listSettings)
            .then(Commands.argument(ARG_SETTING_NAME, StringArgumentType.string())
                .executes(SettingsCommand1_14::listSettings)))
        .then(Commands.literal("reload")
            .executes(reload()));
  }

  private static int sendUsage(CommandContext<CommandSource> context) {
    ListSettingsCommand.sendUsage(new CommandContext1_14(context));
    return 0;
  }

  private static Command<CommandSource> reload() {
    return context -> {
      new ReloadSettingsCommand(new CommandContext1_14(context)).run();
      return 0;
    };
  }

  private static int listSettings(CommandContext<CommandSource> context) {
    com.github.fnar.roguelike.command.CommandContext commandContext = new CommandContext1_14(context);
    String namespace = commandContext.getArgument(ARG_SETTING_NAME).orElse("");
    new ListSettingsCommand(commandContext, namespace).run();
    return 0;
  }

}
