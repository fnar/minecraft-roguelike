package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseRoguelikeCommand implements RoguelikeCommand {

  private static final Logger logger = LogManager.getLogger(BaseRoguelikeCommand.class);
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
    logger.info(exception);
  }

  @Override
  public final void run() {
    try {
      if (onRun()) {
        onSuccess();
      }
    } catch (Exception e) {
      onException(e);
    }
  }
}
