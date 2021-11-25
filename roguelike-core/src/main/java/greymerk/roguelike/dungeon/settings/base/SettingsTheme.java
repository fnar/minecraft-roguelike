package greymerk.roguelike.dungeon.settings.base;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.Themes;

public class SettingsTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "theme");

  private final static Themes[] themes = {Themes.OAK, Themes.SPRUCE, Themes.CRYPT, Themes.MOSSY};
  private final static Themes[] level4Themes = {Themes.CAVE, Themes.HELL, Themes.ICE, Themes.NETHER, Themes.NETHER_FORTRESS};

  public SettingsTheme() {
    super(ID);

    setTowerSettings(new TowerSettings(Tower.ROGUE, Themes.TOWER));
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      Theme theme = i == 4 ? randomTheme().getThemeBase() : themes[i].getThemeBase();
      level.setTheme(theme);
      getLevelSettings().put(i, level);
    }
  }

  private Themes randomTheme() {
    int choice = (int) (Math.random() * SettingsTheme.level4Themes.length);
    return SettingsTheme.level4Themes[choice];
  }
}
