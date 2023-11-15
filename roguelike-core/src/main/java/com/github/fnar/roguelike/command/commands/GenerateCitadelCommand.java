package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.worldgen.Coord;

public class GenerateCitadelCommand extends BaseRoguelikeCommand {

  private final Coord coord;

  public GenerateCitadelCommand(CommandContext commandContext, Coord coord) {
    super(commandContext);
    this.coord = Optional.ofNullable(coord).orElse(commandContext.getSenderCoord());
  }

  @Override
  public boolean onRun() {
    Citadel.generate(context.createEditor(), coord.getX(), coord.getZ());
    return true;
  }
}
