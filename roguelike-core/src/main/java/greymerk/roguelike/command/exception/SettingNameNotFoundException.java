package greymerk.roguelike.command.exception;

public class SettingNameNotFoundException extends Exception {
  public SettingNameNotFoundException(String settingName) {
    super(settingName + " not found.");
  }
}
