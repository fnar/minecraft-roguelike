package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.layout.LayoutGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class BuiltinLayoutSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Layout";

  public BuiltinLayoutSettings() {
    super(ID);
    int[] numRooms = {10, 15, 15, 20, 15};
    int[] scatter = {15, 15, 17, 12, 15};
    int[] range = {50, 50, 80, 70, 50};

    LayoutGenerator.Type[] generator = {
        LayoutGenerator.Type.CLASSIC,
        LayoutGenerator.Type.CLASSIC,
        LayoutGenerator.Type.MST,
        LayoutGenerator.Type.CLASSIC,
        LayoutGenerator.Type.CLASSIC
    };

    for (int level = 0; level < DungeonSettings.MAXIMUM_COUNT_OF_LEVELS; level++) {
      LevelSettings levelSettings = new LevelSettings(level);
      levelSettings.setNumRooms(numRooms[level]);
      levelSettings.setRange(range[level]);
      levelSettings.setScatter(scatter[level]);
      levelSettings.setGenerator(generator[level]);
      getLevelSettings().put(level, levelSettings);
    }
  }


}
