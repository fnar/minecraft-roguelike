package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

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
  public boolean onRun() {
    if (namespace.isEmpty()) {
      context.sendInfo(SettingsResolver.getInstance(context.getModLoader()).toString());
    } else {
      context.sendInfo(SettingsResolver.getInstance(context.getModLoader()).toString(namespace));
    }
    return true;
  }

  @Override
  public void onSuccess() {
    context.sendSuccess("settingslisted");
  }

}
