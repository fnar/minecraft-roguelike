package greymerk.roguelike.dungeon.settings.base;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeBase;

public class SettingsTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "theme");

  private final static Theme[] themes = {Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY};
  private final static Theme[] level4Themes = {Theme.CAVE, Theme.HELL, Theme.ICE, Theme.NETHER, Theme.NETHER_FORTRESS};

  public SettingsTheme() {
    super(ID);

    setTowerSettings(new TowerSettings(Tower.ROGUE, Theme.TOWER));
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      ThemeBase theme = i == 4 ? randomTheme().getThemeBase() : themes[i].getThemeBase();
      level.setTheme(theme);
      getLevelSettings().put(i, level);
    }
  }

  private Theme randomTheme() {
    int choice = (int) (Math.random() * SettingsTheme.level4Themes.length);
    return SettingsTheme.level4Themes[choice];
  }
}
