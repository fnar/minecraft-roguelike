package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class BuiltinSecretsSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "secrets");

  public BuiltinSecretsSettings() {
    super(ID);

    for (int level = 0; level < 5; ++level) {

      SecretsSetting factory = new SecretsSetting();

      switch (level) {
        case 0:
          break;
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
        default:
          break;
      }

      LevelSettings levelSettings = new LevelSettings(level);
      levelSettings.setSecrets(factory);
      getLevelSettings().put(level, levelSettings);
    }
  }
}
