package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.worldgen.Coord;

import static greymerk.roguelike.dungeon.Dungeon.TOPLEVEL;

public class GenerateTowerCommand extends BaseRoguelikeCommand {

  private final String towerName;

  public GenerateTowerCommand(CommandContext context, String towerName) {
    super(context);
    this.towerName = towerName;
  }

  @Override
  public void onRun() throws Exception {

    if (towerName == null) {
      context.sendInfo("notif.roguelike.usage_", "roguelike tower <tower name>");
      context.sendInfo("notif.roguelike.towertypes", String.join(" ", TowerType.getTowerList()));
      return;
    }

    Optional<TowerType> towerType = TowerType.get(towerName);
    if (!towerType.isPresent()) {
      context.sendFailure("nosuchtower", towerName);
      return;
    }

    towerType.map(this::instantiateTower).ifPresent(this::generateTower);
  }

  private void generateTower(Tower tower) {
    tower.generate(getCoord());
  }

  private Tower instantiateTower(TowerType towerType) {
    return towerType.instantiate(context.createEditor());
  }

  private Coord getCoord() {
    return context.getSenderCoord().setY(TOPLEVEL);
  }

  @Override
  public void onSuccess() {
    //context.sendSuccess(towerName + " Tower generated at " + here);
    context.sendSuccess(towerName + "_generated", getCoord().toString());
  }
}
