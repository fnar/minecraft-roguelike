package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.ContextHolder1_14;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;

import greymerk.roguelike.worldgen.Coord;

public class DungeonCommand1_14 implements Command<CommandSource> {

  private final Coord coord;
  private final String settings;

  public DungeonCommand1_14(Coord coord, String settings) {
    this.coord = coord;
    this.settings = settings;
  }

  @Override
  public int run(CommandContext<CommandSource> context) {
    com.github.fnar.roguelike.command.CommandContext commandContext = new com.github.fnar.roguelike.command.CommandContext(new ContextHolder1_14(context));
    new DungeonCommand(commandContext, coord, settings).run();
    return 0;
  }

}
