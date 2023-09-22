package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.ContextHolder1_14;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;

public class BiomeAtPlayerCommand1_14 implements Command<CommandSource> {

  @Override
  public int run(CommandContext<CommandSource> context) {
    new BiomeAtPlayerCommand(new com.github.fnar.roguelike.command.CommandContext(new ContextHolder1_14(context))).run();
    return 0;
  }

}
