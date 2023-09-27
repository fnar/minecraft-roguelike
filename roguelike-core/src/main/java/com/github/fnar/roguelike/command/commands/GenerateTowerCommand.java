package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class GenerateTowerCommand extends BaseRoguelikeCommand {

  private final TowerType towerType;
  private final Coord coord;

  public GenerateTowerCommand(CommandContext commandContext, TowerType towerType, Coord coord) {
    super(commandContext);
    this.towerType = towerType;
    this.coord = coord;
  }

  @Override
  public void onRun() throws Exception {
    WorldEditor worldEditor = commandContext.createEditor();
    Theme theme = TowerType.getDefaultTheme(towerType).getThemeBase();
    Tower tower = towerType.instantiate(worldEditor, theme);
    tower.generate(coord);
  }

}
