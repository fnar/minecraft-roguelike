package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.ContextHolder1_14;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class SettingsCommand1_14 implements Command<CommandSource> {

  public static final String ARG_NAMESPACE = "namespace";

  public static LiteralArgumentBuilder<CommandSource> settingsCommand() {
    return Commands.literal("settings")
        .executes(new SettingsCommand1_14())
        .then(Commands.argument(ARG_NAMESPACE, StringArgumentType.string())
            .executes(new SettingsCommand1_14()));
  }

  @Override
  public int run(CommandContext<CommandSource> context) {
    com.github.fnar.roguelike.command.CommandContext commandContext = new com.github.fnar.roguelike.command.CommandContext(new ContextHolder1_14(context));
    String namespace = commandContext.getArgument(ARG_NAMESPACE).orElse("");
    new ListSettingsCommand(commandContext, namespace).run();
    return 0;
  }

}
