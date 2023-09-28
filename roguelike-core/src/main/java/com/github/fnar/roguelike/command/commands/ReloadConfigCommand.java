package com.github.fnar.roguelike.command.commands;

import com.github.fnar.roguelike.command.CommandContext;

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
    context.sendSuccess("configreloaded");
  }

}
