package com.github.fnar.roguelike.command;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ReloadSettingsCommand extends BaseRoguelikeCommand {

  public ReloadSettingsCommand(CommandContext commandContext) {
    super(commandContext);
  }

  @Override
  public void onRun() {
    SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader()).loadFiles();
    SettingsResolver.instance = new SettingsResolver(settingsContainer);
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("settingsreloaded");
  }

}
