package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ReloadSettingsCommand extends BaseRoguelikeCommand {

  public ReloadSettingsCommand(CommandContext commandContext) {
    super(commandContext);
  }

  @Override
  public void onRun() {
    SettingsContainer settingsContainer = new SettingsContainer(context.getModLoader()).loadFiles();
    SettingsResolver.instance = new SettingsResolver(settingsContainer);
  }

  @Override
  public void onSuccess() {
    context.sendSuccess("settingsreloaded");
  }

}
