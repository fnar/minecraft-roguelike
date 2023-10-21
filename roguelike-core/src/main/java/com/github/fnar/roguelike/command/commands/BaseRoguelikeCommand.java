package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

public class BaseRoguelikeCommand implements RoguelikeCommand {

  protected final CommandContext context;

  public BaseRoguelikeCommand(CommandContext context) {
    this.context = context;
  }

  @Override
  public void onRun() throws Exception {

  }

  @Override
  public void onSuccess() {
    // todo: internationalization
    //context.sendSuccess("Successful command.");
    context.sendSuccess("notif.roguelike.success_command");
  }

  @Override
  public void onException(Exception exception) {
    context.sendFailure(exception);
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
