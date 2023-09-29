package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.world.BiomeTag;

import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Themes;

public class BuiltinMesaDungeonSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Mesa";

  public BuiltinMesaDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().addBiomeTags(BiomeTag.MESA);
    setTowerSettings(new TowerSettings(TowerType.ETHO, Themes.ETHOTOWER));

    Themes[] themes = {Themes.ETHOTOWER, Themes.ETHOTOWER, Themes.CRYPT, Themes.CRYPT, Themes.NETHER};
    IntStream.range(0, DungeonSettings.MAXIMUM_COUNT_OF_LEVELS)
        .forEach(level -> getLevelSettings(level).setTheme(themes[level].getThemeBase()));
  }
}
