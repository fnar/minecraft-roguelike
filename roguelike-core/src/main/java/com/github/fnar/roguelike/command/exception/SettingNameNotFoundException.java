package com.github.fnar.roguelike.command.exception;

public class SettingNameNotFoundException extends Exception {
  public SettingNameNotFoundException(String settingName) {
    super(settingName + " not found.");
  }
}
