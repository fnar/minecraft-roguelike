package greymerk.roguelike.dungeon.settings.builtin.dungeon;

import com.github.fnar.minecraft.item.Ingredient;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.world.BiomeTag;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.base.RoomsSetting;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.builtin.BuiltinBaseSettings;
import greymerk.roguelike.dungeon.towers.TowerType;
import greymerk.roguelike.theme.Themes;
import greymerk.roguelike.treasure.loot.MinecraftItemLootItem;
import greymerk.roguelike.treasure.loot.rule.ForEachLootRule;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.filter.Filter;

public class BuiltinSwampDungeonSettings extends DungeonSettings {

  public static final String ID = SettingsContainer.BUILTIN_NAMESPACE + "Swamp";

  public BuiltinSwampDungeonSettings() {
    super(ID);
    setExclusive(true);
    getInherit().add(BuiltinBaseSettings.ID);
    getCriteria().addBiomeTags(BiomeTag.SWAMP);
    setTowerSettings(new TowerSettings(TowerType.WITCH, Themes.DARKOAK));

    Themes[] themes = {
        Themes.DARKHALL,
        Themes.DARKHALL,
        Themes.MUDDY,
        Themes.MOSSY,
        Themes.NETHER
    };

    WeightedRandomizer<RldItemStack> brewing = new WeightedRandomizer<>();
    brewing.add(new MinecraftItemLootItem(Ingredient.Type.GLASS_BOTTLE.asItem(), 0, 1, 3, 3));
    brewing.add(new MinecraftItemLootItem(Ingredient.Type.MAGMA_CREAM.asItem(), 0, 1, 2, 1));
    brewing.add(new MinecraftItemLootItem(Ingredient.Type.GLISTERING_MELON_SLICE.asItem(), 0, 1, 3, 1));
    brewing.add(new MinecraftItemLootItem(Ingredient.Type.BLAZE_POWDER.asItem(), 0, 1, 3, 1));
    brewing.add(new MinecraftItemLootItem(Ingredient.Type.SUGAR.asItem(), 0, 1, 3, 1));
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new ForEachLootRule(brewing, i, 2));
      getLootRules().add(new SingleUseLootRule(new MinecraftItemLootItem(Material.Type.SLIME_BALL.asItem(), 0, 1, 1 + i, 1), i, 4 + i * 3));
    }
    for (int level = 0; level < DungeonSettings.MAXIMUM_COUNT_OF_LEVELS; level++) {

      LevelSettings levelSettings = getLevelSettings(level);
      levelSettings.setTheme(themes[level].getThemeBase());

      if (level == 0) {

        SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
        segments.add(Segment.DOOR, 8);
        segments.add(Segment.LAMP, 2);
        segments.add(Segment.FLOWERS, 1);
        segments.add(Segment.MUSHROOM, 2);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.CAKE.newSingleRoomSetting());
        factory.add(RoomType.DARKHALL.newSingleRoomSetting());
        factory.add(RoomType.BRICK.newRandomRoomSetting(10));
        factory.add(RoomType.CORNER.newRandomRoomSetting(3));
        levelSettings.setRooms(factory);
        levelSettings.addFilter(Filter.MUD);
      }

      if (level == 1) {

        SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
        segments.add(Segment.DOOR, 8);
        segments.add(Segment.SHELF, 4);
        segments.add(Segment.INSET, 4);
        segments.add(Segment.MUSHROOM, 3);
        levelSettings.setSegments(segments);

        RoomsSetting factory = new RoomsSetting();
        factory.add(RoomType.CAKE.newSingleRoomSetting());
        factory.add(RoomType.LAB.newSingleRoomSetting());
        factory.add(RoomType.SPIDER.newSingleRoomSetting());
        factory.add(RoomType.PIT.newSingleRoomSetting());
        factory.add(RoomType.PRISON.newSingleRoomSetting());
        factory.add(RoomType.BRICK.newRandomRoomSetting(10));
        factory.add(RoomType.CORNER.newRandomRoomSetting(3));
        levelSettings.setRooms(factory);
        levelSettings.addFilter(Filter.MUD);
      }
    }

    getLevelSettings(2).addFilter(Filter.VINE);
    getLevelSettings(3).addFilter(Filter.VINE);
  }
}
