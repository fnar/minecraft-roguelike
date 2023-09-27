package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.ContextHolder1_14;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;

import greymerk.roguelike.worldgen.Coord;

public class DungeonCommand1_14 {

  public static final String ARG_SETTINGS_NAME = "settingsName";
  public static final String ARG_COORD = "coord";

  public static LiteralArgumentBuilder<CommandSource> dungeonCommand() {
    return Commands.literal("dungeon")
        .then(Commands.literal("here")
            .executes(DungeonCommand1_14::generateDungeon)
            .then(Commands.argument(ARG_SETTINGS_NAME, StringArgumentType.string())
                .executes(DungeonCommand1_14::generateDungeon)))
        .then(Commands.literal("nearby")
            .executes(DungeonCommand1_14::generateDungeon)
            .then(Commands.argument(ARG_SETTINGS_NAME, StringArgumentType.string())
                .executes(DungeonCommand1_14::generateDungeon)))
        .then(Commands.argument(ARG_COORD, BlockPosArgument.blockPos())
            .executes(DungeonCommand1_14::generateDungeon)
            .then(Commands.argument(ARG_SETTINGS_NAME, StringArgumentType.string())
                .executes(DungeonCommand1_14::generateDungeon)))
        .executes(DungeonCommand1_14::generateDungeon);
  }

  private static int generateDungeon(CommandContext<CommandSource> context) {
    com.github.fnar.roguelike.command.CommandContext context1_14 = new com.github.fnar.roguelike.command.CommandContext(new ContextHolder1_14(context));
    Coord coord = context1_14.getArgumentAsCoord(ARG_COORD).orElse(null);
    String settingName = context1_14.getArgument(ARG_SETTINGS_NAME).orElse(null);
    new DungeonCommand(context1_14, coord, settingName).run();
    return 0;
  }

}
