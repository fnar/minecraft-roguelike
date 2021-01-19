package greymerk.roguelike.dungeon.settings.builtin;

import net.minecraft.init.Items;

import greymerk.roguelike.dungeon.LevelGenerator;
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
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.SANDY;

public class SettingsDesertTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "desert");

  public SettingsDesertTheme() {
    super(ID);
    setExclusive(true);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(SANDY));
    setTowerSettings(new TowerSettings(Tower.PYRAMID, Theme.PYRAMID));
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new SingleUseLootRule(new WeightedRandomLoot(Items.GOLD_INGOT, 0, 1, 1 + i, 1), i, 6));
    }

    Theme[] themes = {Theme.PYRAMID, Theme.SANDSTONE, Theme.SANDSTONERED, Theme.ENDER, Theme.NETHER};

    for (int i = 0; i < 5; ++i) {

      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i].getThemeBase());

      if (i == 0) {
        level.setDifficulty(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.ANKH, 5);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        level.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newSingleRoomSetting());
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(3));
        level.setRooms(factory);

        SecretsSetting secrets = new SecretsSetting();
        secrets.add(RoomType.PYRAMIDTOMB.newSingleRoomSetting());
        level.setSecrets(secrets);

        level.setGenerator(LevelGenerator.CLASSIC);
      }

      if (i == 1) {
        level.setDifficulty(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.ANKH, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        level.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(10));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(5));
        level.setRooms(factory);

        level.setGenerator(LevelGenerator.CLASSIC);
      }

      if (i == 2) {
        level.setDifficulty(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        level.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(1));
        factory.add(RoomType.CRYPT.newRandomRoomSetting(4));
        factory.add(RoomType.OSSUARY.newSingleRoomSetting());
        factory.add(RoomType.SPIDER.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(4));

        level.setRooms(factory);

        level.setGenerator(LevelGenerator.CLASSIC);
      }

      if (i == 3) {
        level.setDifficulty(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        level.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(1));
        factory.add(RoomType.SLIME.newRandomRoomSetting(2));
        factory.add(RoomType.FIRE.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(4));
        factory.add(RoomType.SPIDER.newRandomRoomSetting(2));
        level.setRooms(factory);

        level.setGenerator(LevelGenerator.CLASSIC);
      }

      getLevels().put(i, level);
    }
  }
}