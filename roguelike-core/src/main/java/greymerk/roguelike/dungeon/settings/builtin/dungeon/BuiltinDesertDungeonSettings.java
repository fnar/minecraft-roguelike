package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.item.Material;

import greymerk.roguelike.dungeon.layout.LayoutGenerator;
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
import greymerk.roguelike.theme.Themes;
import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.SANDY;

public class BuiltinDesertDungeonSettings extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "desert");

  public BuiltinDesertDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().setBiomeTypes(newArrayList(SANDY));
    setTowerSettings(new TowerSettings(TowerType.PYRAMID, Themes.PYRAMID));
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new SingleUseLootRule(new MinecraftItemLootItem(Material.Type.GOLD_INGOT.asItem(), 0, 1, 1 + i, 1), i, 6));
    }

    Themes[] themes = {Themes.PYRAMID, Themes.SANDSTONE, Themes.SANDSTONERED, Themes.ENDER, Themes.NETHER};

    for (int level = 0; level < MAXIMUM_COUNT_OF_LEVELS; level++) {

      LevelSettings levelSettings = new LevelSettings(level);
      levelSettings.setTheme(themes[level].getThemeBase());

      if (level == 0) {
        levelSettings.setLevel(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.ANKH, 5);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newSingleRoomSetting());
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(3));
        levelSettings.setRooms(factory);

        SecretsSetting secrets = new SecretsSetting();
        secrets.add(RoomType.PYRAMIDTOMB.newSingleRoomSetting());
        levelSettings.setSecrets(secrets);

        levelSettings.setGenerator(LayoutGenerator.Type.CLASSIC);
      }

      if (level == 1) {
        levelSettings.setLevel(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.ANKH, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(10));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(5));
        levelSettings.setRooms(factory);

        levelSettings.setGenerator(LayoutGenerator.Type.CLASSIC);
      }

      if (level == 2) {
        levelSettings.setLevel(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(1));
        factory.add(RoomType.CRYPT.newRandomRoomSetting(4));
        factory.add(RoomType.OSSUARY.newSingleRoomSetting());
        factory.add(RoomType.SPIDER.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(4));

        levelSettings.setRooms(factory);

        levelSettings.setGenerator(LayoutGenerator.Type.CLASSIC);
      }

      if (level == 3) {
        levelSettings.setLevel(2);
        SegmentGenerator segments = new SegmentGenerator(Segment.SQUAREARCH);
        segments.add(Segment.SPAWNER, 1);
        segments.add(Segment.WALL, 10);
        segments.add(Segment.INSET, 5);
        segments.add(Segment.SHELF, 5);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.SKULL, 2);
        segments.add(Segment.TOMB, 1);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.PYRAMIDTOMB.newRandomRoomSetting(1));
        factory.add(RoomType.SLIME.newRandomRoomSetting(2));
        factory.add(RoomType.FIRE.newRandomRoomSetting(2));
        factory.add(RoomType.PYRAMIDSPAWNER.newRandomRoomSetting(5));
        factory.add(RoomType.PYRAMIDCORNER.newRandomRoomSetting(4));
        factory.add(RoomType.SPIDER.newRandomRoomSetting(2));
        levelSettings.setRooms(factory);

        levelSettings.setGenerator(LayoutGenerator.Type.CLASSIC);
      }

      getLevelSettings().put(level, levelSettings);
    }
  }
}
