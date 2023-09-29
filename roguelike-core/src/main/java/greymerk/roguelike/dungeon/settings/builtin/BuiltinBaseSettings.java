package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class BuiltinBaseSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Base";

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
