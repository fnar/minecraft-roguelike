package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

public class BaseRoguelikeCommand implements RoguelikeCommand {

  protected final CommandContext context;

  public BaseRoguelikeCommand(CommandContext context) {
    this.context = context;
  }

  @Override
  public boolean onRun() throws Exception {
    return false;
  }

  @Override
  public void onSuccess() {
    // todo: internationalization
    //context.sendSuccess("Successful command.");
    context.sendSuccess("command");
  }

  @Override
  public void onException(Exception exception) {
    context.sendFailure(exception);
  }

  @Override
  public final void run() {
    try {
      if ( onRun() )
        onSuccess();
    } catch (Exception e) {
      onException(e);
    }
  }
}
