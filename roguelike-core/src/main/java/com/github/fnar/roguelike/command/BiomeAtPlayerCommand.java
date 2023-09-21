package com.github.fnar.roguelike.command;

import java.util.List;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class BiomeAtPlayerCommand extends BaseRoguelikeCommand {

  public BiomeAtPlayerCommand(CommandContext commandContext) {
    super(commandContext);
  }

  @Override
  public void onRun() {
    sendBiomeDetails(commandContext, commandContext.getPos());
  }

  @Override
  public void onSuccess() {
    // todo: Add to language files
    commandContext.sendSuccess("biomeslisted");
  }

  protected void sendBiomeDetails(CommandContext commandContext, Coord coord) {
    commandContext.sendSpecial("notif.roguelike.biomeinfo", coord.toString());

    WorldEditor editor = commandContext.createEditor();
    commandContext.sendSpecial(editor.getBiomeName(coord));

    List<String> typeNames = editor.getBiomeTagNames(coord);
    commandContext.sendSpecial(String.join("", typeNames));
  }

}
