package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.world.BlockPosMapper1_14;
import com.github.fnar.roguelike.command.commands.BiomeAtLocationCommand1_14;
import com.github.fnar.roguelike.command.commands.BiomeAtPlayerCommand1_14;
import com.github.fnar.roguelike.command.commands.DungeonCommand1_14;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;

public class RoguelikeCommands {

  public static final String SETTINGS_NAME_ARG = "settingsName";

  public static final int CREATIVE_PERMISSION_LEVEL = 3;

  public static LiteralArgumentBuilder<CommandSource> roguelikeCommand() {
    return Commands.literal("roguelike")
        .requires(RoguelikeCommands::isCreativePlayer)
        .then(biomeCommand())
        .then(dungeonCommand());
  }

  private static LiteralArgumentBuilder<CommandSource> biomeCommand() {
    return Commands.literal("biome")
        .executes(new BiomeAtPlayerCommand1_14())
        .then(Commands.argument("position", BlockPosArgument.blockPos())
            .executes(new BiomeAtLocationCommand1_14()));
  }

  private static LiteralArgumentBuilder<CommandSource> dungeonCommand() {
    return Commands.literal("dungeon")
        .then(Commands.literal("here")
            .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(context.getSource().getEntity().getPosition()), null).run(context))
            .then(Commands.argument(SETTINGS_NAME_ARG, StringArgumentType.string())
                .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(context.getSource().getEntity().getPosition()), context.getArgument(SETTINGS_NAME_ARG, String.class)).run(context))
            )
        )
        .then(Commands.literal("nearby")
            .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(context.getSource().getEntity().getPosition()), null).run(context))
            .then(Commands.argument(SETTINGS_NAME_ARG, StringArgumentType.string())
                .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(context.getSource().getEntity().getPosition()), context.getArgument(SETTINGS_NAME_ARG, String.class)).run(context))
            )
        )
        .then(Commands.argument("position", BlockPosArgument.blockPos())
            .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(BlockPosArgument.getBlockPos(context, "position")), null).run(context))
            .then(Commands.argument(SETTINGS_NAME_ARG, StringArgumentType.string())
                .executes(context -> new DungeonCommand1_14(BlockPosMapper1_14.map(BlockPosArgument.getBlockPos(context, "position")), context.getArgument(SETTINGS_NAME_ARG, String.class)).run(context))
            )
        )
        ;
  }

  private static boolean isCreativePlayer(CommandSource commandSource) {
    return commandSource.hasPermissionLevel(CREATIVE_PERMISSION_LEVEL);
  }

}
