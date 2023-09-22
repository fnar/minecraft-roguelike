package com.github.fnar.roguelike.settings.exception;

public class SettingsNotFoundException extends RuntimeException {
  public SettingsNotFoundException(String settingName) {
    super("No settings with id " + settingName + " found.");
  }
}
