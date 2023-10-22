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

  private final static Theme.Type[] themes = {Theme.Type.OAK, Theme.Type.SPRUCE, Theme.Type.CRYPT, Theme.Type.MOSSY};
  private final static Theme.Type[] level4Themes = {Theme.Type.CAVE, Theme.Type.HELL, Theme.Type.ICE, Theme.Type.NETHER, Theme.Type.NETHER_FORTRESS};

  public BuiltinThemeSettings() {
    super(ID);

    setTowerSettings(new TowerSettings(TowerType.ROGUE, Theme.Type.TOWER));
    for (int level = 0; level < MAXIMUM_COUNT_OF_LEVELS; level++) {
      LevelSettings levelSettings = new LevelSettings(level);
      Theme theme = level == 4 ? randomTheme().asTheme() : themes[level].asTheme();
      levelSettings.setTheme(theme);
      getLevelSettings().put(level, levelSettings);
    }
  }

  private Theme.Type randomTheme() {
    int choice = (int) (Math.random() * BuiltinThemeSettings.level4Themes.length);
    return BuiltinThemeSettings.level4Themes[choice];
  }
}
