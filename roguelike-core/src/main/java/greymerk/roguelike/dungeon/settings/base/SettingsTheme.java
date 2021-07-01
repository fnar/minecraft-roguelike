package greymerk.roguelike.dungeon.settings.base;

import com.github.fnar.roguelike.theme.ThemeBases;

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

  private final static ThemeBase[] themes = {ThemeBases.OAK, ThemeBases.SPRUCE, ThemeBases.CRYPT, ThemeBases.MOSSY};
  private final static ThemeBase[] level4Themes = {ThemeBases.CAVE, ThemeBases.HELL, ThemeBases.ICE, ThemeBases.NETHER, ThemeBases.NETHER_FORTRESS};

  public SettingsTheme() {
    super(ID);

    setTowerSettings(new TowerSettings(Tower.ROGUE, ThemeBases.TOWER));
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      ThemeBase theme = i == 4 ? Theme.randomThemeBase() : themes[i];
      level.setTheme(theme);
      getLevelSettings().put(i, level);
    }
  }

  private ThemeBase randomTheme() {
    int choice = (int) (Math.random() * SettingsTheme.level4Themes.length);
    return SettingsTheme.level4Themes[choice];
  }
}
