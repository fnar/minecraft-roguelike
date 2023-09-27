package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ListSettingsCommand extends BaseRoguelikeCommand {

  private final String namespace;

  public ListSettingsCommand(CommandContext commandContext, String namespace) {
    super(commandContext);
    this.namespace = Optional.ofNullable(namespace).orElse("");
  }

  public static void sendUsage(CommandContext commandContext) {
    commandContext.sendInfo("notif.roguelike.usage_", "roguelike settings [reload | list]");
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
