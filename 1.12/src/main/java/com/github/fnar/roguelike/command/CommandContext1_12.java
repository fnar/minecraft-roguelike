package com.github.fnar.roguelike.command;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.forge.ModLoader1_12;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandContext1_12 extends CommandContext {

  private final ModLoader modLoader;
  public CommandContext1_12(ICommandSender sender, List<String> args) {
    super(new ContextHolder1_12(sender, args));
    modLoader = new ModLoader1_12();
  }

  @Override
  public ModLoader getModLoader() {
    return modLoader;
  }
}
