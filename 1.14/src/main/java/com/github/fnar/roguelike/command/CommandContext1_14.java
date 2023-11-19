package com.github.fnar.roguelike.command;

import net.minecraft.command.CommandSource;

public class CommandContext1_14 extends CommandContext {

  public CommandContext1_14(com.mojang.brigadier.context.CommandContext<CommandSource> context) {
    super(new ContextHolder1_14(context));
  }

}
