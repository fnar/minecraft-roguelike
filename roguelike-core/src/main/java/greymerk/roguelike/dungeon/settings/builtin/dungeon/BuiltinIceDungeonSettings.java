package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.world.BiomeTag;

import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Themes;

public class BuiltinIceDungeonSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Ice";

  public BuiltinIceDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().addBiomeTags(BiomeTag.SNOWY);
    setTowerSettings(new TowerSettings(TowerType.PYRAMID, Themes.ICE));
    IntStream.range(0, MAXIMUM_COUNT_OF_LEVELS)
        .mapToObj(this::getLevelSettings)
        .forEach(levelSettings -> levelSettings.setTheme(Themes.ICE));
  }

}
