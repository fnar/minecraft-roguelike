package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;

public class BuiltinThemeSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "theme");

  private final static Theme[] themes = {Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY};
  private final static Theme[] level4Themes = {Theme.CAVE, Theme.HELL, Theme.ICE, Theme.NETHER, Theme.NETHER_FORTRESS};

  public BuiltinThemeSettings() {
    super(ID);

    setTowerSettings(new TowerSettings(TowerType.ROGUE, Theme.TOWER));
    for (int level = 0; level < MAXIMUM_COUNT_OF_LEVELS; level++) {
      LevelSettings levelSettings = new LevelSettings(level);
      Theme theme = level == 4 ? randomTheme() : themes[level];
      levelSettings.setTheme(theme);
      getLevelSettings().put(level, levelSettings);
    }
  }

  private Theme randomTheme() {
    int choice = (int) (Math.random() * BuiltinThemeSettings.level4Themes.length);
    return BuiltinThemeSettings.level4Themes[choice];
  }
}
