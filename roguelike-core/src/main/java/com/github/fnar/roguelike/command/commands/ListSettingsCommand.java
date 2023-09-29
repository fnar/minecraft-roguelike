package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ListSettingsCommand extends BaseRoguelikeCommand {

  private final String name;

  public ListSettingsCommand(CommandContext commandContext, String name) {
    super(commandContext);
    this.name = Optional.ofNullable(name).orElse("");
  }

  public static void sendUsage(CommandContext commandContext) {
    commandContext.sendInfo("notif.roguelike.usage_", "roguelike settings [reload | list]");
  }

  @Override
  public void onRun() {
    SettingsContainer settingsContainer = new SettingsContainer(context.getModLoader()).loadFiles();
    SettingsResolver.instance = new SettingsResolver(settingsContainer);
    if (name.isEmpty()) {
      context.sendInfo(SettingsResolver.instance.toString());
    } else {
      context.sendInfo(SettingsResolver.instance.toString(name));
    }
  }

  @Override
  public void onSuccess() {
    context.sendSuccess("settingslisted");
  }

}
