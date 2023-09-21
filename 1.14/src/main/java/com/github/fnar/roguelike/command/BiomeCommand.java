package com.github.fnar.roguelike.command;

import com.mojang.brigadier.Command;

import net.minecraft.command.CommandSource;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class BiomeCommand {

  protected final CommandContext commandContext;

  public BiomeCommand(CommandContext commandContext) {
    this.commandContext = commandContext;
  }

  public static Command<CommandSource> biomeCommand() {
    return context -> new BiomeCommand(new CommandContext(new ContextHolder1_14(context))).execute();
  }

  public int execute() {
    sendBiomeDetails(commandContext, commandContext.getPos());
    return 0;
  }

  protected void sendBiomeDetails(CommandContext commandContext, Coord coord) {
    commandContext.sendSpecial("notif.roguelike.biomeinfo", coord.toString());

    WorldEditor editor = commandContext.createEditor();
    commandContext.sendSpecial(editor.getBiomeName(coord));

    List<String> typeNames = editor.getBiomeTagNames(coord);
    commandContext.sendSpecial(String.join("", typeNames));
  }
}
