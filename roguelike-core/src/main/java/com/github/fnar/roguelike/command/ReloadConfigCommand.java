package com.github.fnar.roguelike.command;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.config.RogueConfig;

public class ReloadConfigCommand extends BaseRoguelikeCommand {
  public ReloadConfigCommand(CommandContext commandContext) {
    super(commandContext);
  }

  @Override
  public void onRun() throws Exception {
    RogueConfig.reload(true);
  }

  @Override
  public void onSuccess() {
    commandContext.sendSuccess("configreloaded");
  }

}
