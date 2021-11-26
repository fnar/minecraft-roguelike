package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class BuiltinBaseSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base");

  public BuiltinBaseSettings() {
    super(ID);
    getInherit().add(BuiltinRoomsSettings.ID);
    getInherit().add(BuiltinSecretsSettings.ID);
    getInherit().add(BuiltinSegmentsSettings.ID);
    getInherit().add(BuiltinLayoutSettings.ID);
    getInherit().add(BuiltinThemeSettings.ID);
    getInherit().add(BuiltinLootSettings.ID);
  }
}
