package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext1_14;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class GiveCommand1_14 {

  public static final String ITEM_NAME_ARG = "item name";

  public static ArgumentBuilder<CommandSource, ?> giveCommand() {
    return Commands.literal("give")
        .executes(GiveCommand1_14::giveNoveltyItem)
        .then(Commands.argument(ITEM_NAME_ARG, StringArgumentType.string())
            .executes(GiveCommand1_14::giveNoveltyItem));
  }

  private static int giveNoveltyItem(CommandContext<CommandSource> context) {
    CommandContext1_14 commandContext = new CommandContext1_14(context);
    String itemName = commandContext.getArgument(ITEM_NAME_ARG).orElse(null);
    new GiveCommand(commandContext, itemName).run();
    return 0;
  }

}
