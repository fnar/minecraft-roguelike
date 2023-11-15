package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.function.Consumer;

public class SimpleRoguelikeCommand extends BaseRoguelikeCommand {

  private final Runnable onRun;
  private final Runnable onSuccess;
  private final Consumer<Exception> onException;

  public SimpleRoguelikeCommand(
      CommandContext commandContext,
      Runnable onRun,
      Runnable onSuccess,
      Consumer<Exception> onException
  ) {
    super(commandContext);
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
  public boolean onRun() {
    this.onRun.run();
    return true;
  }
}
