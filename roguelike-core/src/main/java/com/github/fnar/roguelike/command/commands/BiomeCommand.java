package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class BiomeCommand extends BaseRoguelikeCommand {

  private final Coord coord;

  public BiomeCommand(CommandContext commandContext, Coord coord) {
    super(commandContext);
    this.coord = Optional.ofNullable(coord).orElse(commandContext.getSenderCoord());
  }

  @Override
  public void onRun() {
    commandContext.sendSpecial("notif.roguelike.biomeinfo", coord.toString());

    WorldEditor editor = commandContext.createEditor();
    commandContext.sendSpecial(editor.getBiomeName(coord));

    List<String> typeNames = editor.getBiomeTagNames(coord);
    commandContext.sendSpecial(String.join("", typeNames));
  }

  @Override
  public void onSuccess() {
    // todo: Add to language files
    commandContext.sendSuccess("biomeslisted");
  }

}
