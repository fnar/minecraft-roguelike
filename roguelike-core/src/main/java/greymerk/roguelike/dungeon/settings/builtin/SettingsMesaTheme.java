package greymerk.roguelike.dungeon.settings.builtin;

import com.github.fnar.roguelike.theme.ThemeBases;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ThemeBase;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.MESA;

public class SettingsMesaTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mesa");

  public SettingsMesaTheme() {
    super(ID);
    setExclusive(true);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(MESA));
    setTowerSettings(new TowerSettings(Tower.ETHO, ThemeBases.ETHO_TOWER));

    ThemeBase[] themes = {ThemeBases.ETHO_TOWER, ThemeBases.ETHO_TOWER, ThemeBases.CRYPT, ThemeBases.CRYPT, ThemeBases.NETHER};
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i]);
      getLevelSettings().put(i, level);
    }
  }
}
