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

public class BuiltinIceDungeonSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "ice");

  public BuiltinIceDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().addBiomeTags(BiomeTag.SNOWY);
    setTowerSettings(new TowerSettings(TowerType.PYRAMID, Theme.ICE));
    IntStream.range(0, MAXIMUM_COUNT_OF_LEVELS)
        .mapToObj(this::getLevelSettings)
        .forEach(levelSettings -> levelSettings.setTheme(Theme.ICE));
  }

}
