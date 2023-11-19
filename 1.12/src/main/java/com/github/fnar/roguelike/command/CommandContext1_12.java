package com.github.fnar.roguelike.command;

import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandContext1_12 extends CommandContext {

  public CommandContext1_12(ICommandSender sender, List<String> args) {
    super(new ContextHolder1_12(sender, args));
  }

}
