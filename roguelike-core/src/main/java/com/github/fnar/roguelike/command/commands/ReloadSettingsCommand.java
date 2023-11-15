package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ReloadSettingsCommand extends BaseRoguelikeCommand {

  public ReloadSettingsCommand(CommandContext commandContext) {
    super(commandContext);
  }

  @Override
  public boolean onRun() {
    SettingsResolver.getInstance(context.getModLoader());
    return true;
  }

  @Override
  public void onSuccess() {
    context.sendSuccess("settingsreloaded");
  }

}
