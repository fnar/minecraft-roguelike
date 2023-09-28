package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext1_14;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class TowerCommand1_14 {

  public static final String ARG_TOWER_NAME = "tower name";

  public static ArgumentBuilder<CommandSource, ?> towerCommand() {
    return Commands.literal("tower")
        .executes(TowerCommand1_14::generateTower)
        .then(Commands.argument(ARG_TOWER_NAME, StringArgumentType.string())
            .executes(TowerCommand1_14::generateTower));
  }

  private static int generateTower(CommandContext<CommandSource> context) {
    CommandContext1_14 context1_14 = new CommandContext1_14(context);
    new GenerateTowerCommand(context1_14, context1_14.getArgument(ARG_TOWER_NAME).orElse(null)).run();
    return 0;
  }

}
