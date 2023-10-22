package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.world.BiomeTag;

import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;

public class BuiltinMesaDungeonSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mesa");

  public BuiltinMesaDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().addBiomeTags(BiomeTag.MESA);
    setTowerSettings(new TowerSettings(TowerType.ETHO, Theme.Type.ETHOTOWER));

    Theme.Type[] themes = {Theme.Type.ETHOTOWER, Theme.Type.ETHOTOWER, Theme.Type.CRYPT, Theme.Type.CRYPT, Theme.Type.NETHER};
    IntStream.range(0, DungeonSettings.MAXIMUM_COUNT_OF_LEVELS)
        .forEach(level -> getLevelSettings(level).setTheme(themes[level].asTheme()));
  }
}
