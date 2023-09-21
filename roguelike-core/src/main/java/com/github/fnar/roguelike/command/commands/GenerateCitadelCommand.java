package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.worldgen.Coord;

public class GenerateCitadelCommand extends BaseRoguelikeCommand {
  private final Coord coord;

  public GenerateCitadelCommand(CommandContext commandContext, Coord coord) {
    super(commandContext);
    this.coord = coord;
  }

  @Override
  public void onRun() {
    Citadel.generate(commandContext.createEditor(), coord.getX(), coord.getZ());
  }
}
