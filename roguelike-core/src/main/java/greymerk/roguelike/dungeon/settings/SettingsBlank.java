package greymerk.roguelike.dungeon.settings;

public class SettingsBlank extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "blank");

  public SettingsBlank() {
    super(ID);
  }
}
