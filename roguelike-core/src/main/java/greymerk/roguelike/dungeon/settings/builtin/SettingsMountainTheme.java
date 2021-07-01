package greymerk.roguelike.dungeon.settings.builtin;

import com.github.fnar.roguelike.theme.ThemeBases;

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
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.filter.Filter;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.MOUNTAIN;

public class SettingsMountainTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "mountain");

  public SettingsMountainTheme() {
    super(ID);
    setExclusive(true);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(MOUNTAIN));
    setTowerSettings(new TowerSettings(Tower.ENIKO, ThemeBases.OAK));

    ThemeBase[] themes = {ThemeBases.ENIKO, ThemeBases.ENIKO2, ThemeBases.SEWER, ThemeBases.MOSSY, ThemeBases.NETHER};

    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i]);

      if (i == 0) {
        level.setScatter(16);
        level.setRange(60);
        level.setNumRooms(10);

        RoomsSetting factory;

        factory = new RoomsSetting();
        factory.add(RoomType.LIBRARY.newSingleRoomSetting());
        factory.add(RoomType.FIRE.newSingleRoomSetting());
        RoomType.ENIKO.newRandomRoomSetting(10);
        RoomType.CORNER.newRandomRoomSetting(3);
        level.setRooms(factory);

        SecretsSetting secrets = new SecretsSetting();
        secrets.add(RoomType.BEDROOM.newSingleRoomSetting());
        secrets.add(RoomType.BEDROOM.newSingleRoomSetting());
        secrets.add(RoomType.SMITH.newSingleRoomSetting());
        level.setSecrets(secrets);

        SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
        segments.add(Segment.DOOR, 7);
        segments.add(Segment.ANKH, 2);
        segments.add(Segment.PLANT, 3);
        segments.add(Segment.LAMP, 1);
        segments.add(Segment.FLOWERS, 1);
        level.setSegments(segments);
      }

      if (i == 1) {
        level.setScatter(16);
        level.setRange(80);
        level.setNumRooms(20);

        RoomsSetting factory;
        factory = new RoomsSetting();
        factory.add(RoomType.FIRE.newSingleRoomSetting());
        factory.add(RoomType.MESS.newSingleRoomSetting());
        factory.add(RoomType.LIBRARY.newSingleRoomSetting());
        factory.add(RoomType.LAB.newSingleRoomSetting());
        factory.add(RoomType.ENIKO.newRandomRoomSetting(10));
        factory.add(RoomType.CORNER.newRandomRoomSetting(3));
        level.setRooms(factory);

        SecretsSetting secrets = new SecretsSetting();
        secrets.add(RoomType.ENCHANT.newSingleRoomSetting());
        level.setSecrets(secrets);

      }

      if (i == 2) {
        level.setDifficulty(4);

        SegmentGenerator segments = new SegmentGenerator(Segment.SEWERARCH);
        segments.add(Segment.SEWER, 7);
        segments.add(Segment.SEWERDRAIN, 4);
        segments.add(Segment.SEWERDOOR, 2);
        level.setSegments(segments);

        RoomsSetting factory;
        factory = new RoomsSetting();
        factory.add(RoomType.BRICK.newRandomRoomSetting(4));
        factory.add(RoomType.SLIME.newRandomRoomSetting(7));
        factory.add(RoomType.CORNER.newRandomRoomSetting(3));
        factory.add(RoomType.SPIDER.newRandomRoomSetting(2));
        factory.add(RoomType.PIT.newRandomRoomSetting(2));
        factory.add(RoomType.PRISON.newRandomRoomSetting(3));
        level.setRooms(factory);
      }

      getLevelSettings().put(i, level);
    }
    getLevelSettings().get(3).addFilter(Filter.VINE);
  }
}
