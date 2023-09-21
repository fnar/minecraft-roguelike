package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ListSettingsCommand extends BaseRoguelikeCommand {

  private final String namespace;

  public ListSettingsCommand(CommandContext commandContext, String namespace) {
    super(commandContext);
    this.namespace = namespace;
  }

  @Override
  public void onRun() {
    SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader()).loadFiles();
    SettingsResolver.instance = new SettingsResolver(settingsContainer);

    if (namespace.isEmpty()) {
      commandContext.sendInfo(SettingsResolver.instance.toString());
    } else {
      commandContext.sendInfo(SettingsResolver.instance.toString(namespace));
    }
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("settingslisted");
  }

}
