package com.github.fnar.roguelike.command;

import greymerk.roguelike.command.CommandContext;

public class BaseRoguelikeCommand implements RoguelikeCommand {

  protected final CommandContext commandContext;

  public BaseRoguelikeCommand(CommandContext commandContext) {
    this.commandContext = commandContext;
  }

  @Override
  public void onRun() throws Exception {

  }

  @Override
  public void onSuccess() {
    // todo: internationalization
    commandContext.sendSuccess("Successful command.");
  }

  @Override
  public void onException(Exception exception) {
    commandContext.sendFailure(exception);
  }

  @Override
  public final void run() {
    try {
      onRun();
      onSuccess();
    } catch (Exception e) {
      onException(e);
    }
  }
}
