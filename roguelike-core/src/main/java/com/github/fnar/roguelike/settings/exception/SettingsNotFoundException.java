package com.github.fnar.roguelike.settings.exception;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;

public class SettingsNotFoundException extends RuntimeException {
  public SettingsNotFoundException(SettingIdentifier settingIdentifier) {
    this(settingIdentifier.toString());
  }

  public SettingsNotFoundException(String settingName) {
    super("No settings with id " + settingName + " found.");
  }
}
