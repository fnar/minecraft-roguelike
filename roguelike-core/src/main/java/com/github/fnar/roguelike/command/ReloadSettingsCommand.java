package com.github.fnar.roguelike.command;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ReloadSettingsCommand implements RoguelikeCommand {

  protected final CommandContext commandContext;

  public ReloadSettingsCommand(CommandContext commandContext) {
    this.commandContext = commandContext;
  }

  @Override
  public void run() {
    try {
      SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader()).loadFiles();
      SettingsResolver.instance = new SettingsResolver(settingsContainer);
    } catch (Exception exception) {
      onException(exception);
      return;
    }
    onSuccess();
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("settingsreloaded");
  }

  @Override
  public void onException(Exception exception) {
    commandContext.sendFailure(exception);
  }
}
