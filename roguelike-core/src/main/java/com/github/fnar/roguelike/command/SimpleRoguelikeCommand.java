package com.github.fnar.roguelike.command;

import java.util.function.Consumer;

public class SimpleRoguelikeCommand implements RoguelikeCommand {

  private final Runnable onRun;
  private final Runnable onSuccess;
  private final Consumer<Exception> onException;

  public SimpleRoguelikeCommand(
      Runnable onRun,
      Runnable onSuccess,
      Consumer<Exception> onException
  ) {
    this.onRun = onRun;
    this.onSuccess = onSuccess;
    this.onException = onException;
  }
  @Override
  public void onSuccess() {
    onSuccess.run();
  }

  @Override
  public void onException(Exception exception) {
    onException.accept(exception);
  }

  @Override
  public void run() {
    try {
      onRun.run();
    } catch (Exception exception) {
      onException(exception);
      return;
    }
    onSuccess();
  }
}
