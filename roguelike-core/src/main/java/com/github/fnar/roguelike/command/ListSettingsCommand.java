package com.github.fnar.roguelike.command;

import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ListSettingsCommand implements RoguelikeCommand {

  private final CommandContext1_12 commandContext;
  private final String namespace;

  public ListSettingsCommand(CommandContext1_12 commandContext, String namespace) {
    this.commandContext = commandContext;
    this.namespace = namespace;
  }

  @Override
  public void run() {
    try {
      SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader()).loadFiles();
      SettingsResolver.instance = new SettingsResolver(settingsContainer);

      if (namespace.isEmpty()) {
        commandContext.sendInfo(SettingsResolver.instance.toString());
      } else {
        commandContext.sendInfo(SettingsResolver.instance.toString(namespace));
      }
    } catch (Exception exception) {
      onException(exception);
      return;
    }
    onSuccess();
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("settingslisted");
  }

  @Override
  public void onException(Exception exception) {
    commandContext.sendFailure(exception);
  }

}
