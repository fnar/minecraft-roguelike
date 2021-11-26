package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Themes;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.MESA;

public class BuiltinMesaDungeonSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mesa");

  public BuiltinMesaDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().setBiomeTypes(newArrayList(MESA));
    setTowerSettings(new TowerSettings(Tower.ETHO, Themes.ETHOTOWER));

    Themes[] themes = {Themes.ETHOTOWER, Themes.ETHOTOWER, Themes.CRYPT, Themes.CRYPT, Themes.NETHER};
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i].getThemeBase());
      getLevelSettings().put(i, level);
    }
  }
}
