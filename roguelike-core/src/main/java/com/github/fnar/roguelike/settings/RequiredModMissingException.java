package com.github.fnar.roguelike.settings;

import greymerk.roguelike.config.RogueConfig;

public class RequiredModMissingException extends Exception {

  private static final String MESSAGE_PATTERN = "Expected mod '%s' to be loaded but it could not be found." +
      " Either install the missing mod," +
      " disable `%s` in the roguelike.cfg," +
      " or remove the mod '%s' from the 'requires' field in this DungeonSetting file.";

  public RequiredModMissingException(String missingRequiredMod) {
    super(String.format(MESSAGE_PATTERN, missingRequiredMod, RogueConfig.BREAK_IF_REQUIRED_MOD_IS_MISSING.getName(), missingRequiredMod));
  }
}
