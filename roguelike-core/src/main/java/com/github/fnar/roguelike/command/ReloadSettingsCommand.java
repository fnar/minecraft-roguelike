package com.github.fnar.roguelike.command;

import java.util.function.Consumer;

import greymerk.roguelike.command.CommandContext1_12;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;

public class ReloadSettingsCommand implements RoguelikeCommand {

  private final CommandContext1_12 commandContext;
  private final Runnable onSuccess;
  private final Consumer<Exception> onException;

  public ReloadSettingsCommand(CommandContext1_12 commandContext, Runnable onSuccess, Consumer<Exception> onException) {
    this.commandContext = commandContext;
    this.onSuccess = onSuccess;
    this.onException = onException;
  }

  @Override
  public void run() {
    try {
      SettingsContainer settingsContainer = new SettingsContainer(commandContext.getModLoader());
      settingsContainer.loadFiles();
      Dungeon.settingsResolver = new SettingsResolver(settingsContainer);
    } catch (Exception exception) {
      onException(exception);
      return;
    }
    onSuccess();
  }

  @Override
  public void onSuccess() {
    onSuccess.run();
  }

  @Override
  public void onException(Exception exception) {
    onException.accept(exception);
  }
}
