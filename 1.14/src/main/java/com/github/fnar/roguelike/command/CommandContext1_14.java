package com.github.fnar.roguelike.command;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.forge.ModLoader1_14;
import net.minecraft.command.CommandSource;

public class CommandContext1_14 extends CommandContext {

  private final ModLoader modLoader;
  public CommandContext1_14(com.mojang.brigadier.context.CommandContext<CommandSource> context) {
    super(new ContextHolder1_14(context));
    modLoader = new ModLoader1_14();
  }

  @Override
  public ModLoader getModLoader() {
    return modLoader;
  }

}
