package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.Themes;

public class BuiltinThemeSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Theme";

  private final static Themes[] themes = {Themes.OAK, Themes.SPRUCE, Themes.CRYPT, Themes.MOSSY};
  private final static Themes[] level4Themes = {Themes.CAVE, Themes.HELL, Themes.ICE, Themes.NETHER, Themes.NETHER_FORTRESS};

  public BuiltinThemeSettings() {
    super(ID);

    setTowerSettings(new TowerSettings(TowerType.ROGUE, Themes.TOWER));
    for (int level = 0; level < MAXIMUM_COUNT_OF_LEVELS; level++) {
      LevelSettings levelSettings = new LevelSettings(level);
      Theme theme = level == 4 ? randomTheme().getThemeBase() : themes[level].getThemeBase();
      levelSettings.setTheme(theme);
      getLevelSettings().put(level, levelSettings);
    }
  }

  private Themes randomTheme() {
    int choice = (int) (Math.random() * BuiltinThemeSettings.level4Themes.length);
    return BuiltinThemeSettings.level4Themes[choice];
  }
}
