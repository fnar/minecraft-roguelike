package com.github.fnar.roguelike.command;

import java.util.function.Consumer;

import greymerk.roguelike.dungeon.Dungeon;

public class ReloadSettingsCommand implements RoguelikeCommand {

  private final Runnable onSuccess;
  private final Consumer<Exception> onException;

  public ReloadSettingsCommand(Runnable onSuccess, Consumer<Exception> onException) {
    this.onSuccess = onSuccess;
    this.onException = onException;
  }

  @Override
  public void run() {
    try {
      Dungeon.initResolver();
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
