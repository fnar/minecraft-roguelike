package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.world.BiomeTag;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.filter.Filter;

public class BuiltinForestDungeonSettings {

  public static DungeonSettings create() {
    DungeonSettings dungeonSettings = new DungeonSettings().withId(new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "forest"));
    dungeonSettings.setExclusive(true);
    dungeonSettings.getInherit().add(BuiltinBaseSettings.ID);
    dungeonSettings.getCriteria().addBiomeTags(BiomeTag.FOREST);
    dungeonSettings.setTowerSettings(new TowerSettings(TowerType.ROGUE, Theme.Type.TOWER));

    level0(dungeonSettings);
    level1(dungeonSettings);
    level3(dungeonSettings);

    return dungeonSettings;
  }

  private static void level0(DungeonSettings dungeonSettings) {
    LevelSettings levelSettings = dungeonSettings.getLevelSettings(0);
    RoomsSetting rooms = levelSettings.getRooms();
    rooms.add(RoomType.DARKHALL.newSingleRoomSetting());
    rooms.add(RoomType.LIBRARY.newSingleRoomSetting());

    rooms.add(RoomType.BRICK.newRandomRoomSetting(1));
    rooms.add(RoomType.CORNER.newRandomRoomSetting(6));

    SecretsSetting secrets = levelSettings.getSecrets();
    secrets.add(RoomType.SMITH.newSingleRoomSetting());
    secrets.add(RoomType.BEDROOM.newSingleRoomSetting());

    levelSettings.setTheme(Theme.Type.SPRUCE.asTheme());

    SegmentGenerator segments = levelSettings.getSegments();
    segments.add(Segment.CHEST, 1);
    segments.add(Segment.INSET, 1);
    segments.add(Segment.PLANT, 2);
    segments.add(Segment.WHEAT, 2);
  }

  private static void level1(DungeonSettings dungeonSettings) {
    LevelSettings levelSettings = dungeonSettings.getLevelSettings(1);
    RoomsSetting rooms = levelSettings.getRooms();
    rooms.add(RoomType.MUSIC.newSingleRoomSetting());
    rooms.add(RoomType.PIT.newSingleRoomSetting());
    rooms.add(RoomType.LAB.newSingleRoomSetting());
    rooms.add(RoomType.SLIME.newSingleRoomSetting());
    rooms.add(RoomType.SLIME.newSingleRoomSetting());

    levelSettings.setTheme(Theme.Type.DARKHALL.asTheme());

    SegmentGenerator segments = levelSettings.getSegments();
    segments.add(Segment.DOOR, 10);
    segments.add(Segment.FLOWERS, 2);
    segments.add(Segment.PLANT, 2);
    segments.add(Segment.SHELF, 1);
  }

  private static void level3(DungeonSettings dungeonSettings) {
    dungeonSettings.getLevelSettings().get(3).addFilter(Filter.VINE);
  }
}
