package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.MockChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class DungeonSettingsTest {

  @Before
  public void before() {
    RogueConfig.testing = true;
    Bootstrap.register();
  }

  @Test
  public void overridesMerge() {
    DungeonSettings base = new DungeonSettings();
    DungeonSettings other = new DungeonSettings();
    other.getOverrides().add(SettingsType.LOOTRULES);
    assert (other.getOverrides().contains(SettingsType.LOOTRULES));

    DungeonSettings merge = new DungeonSettings(base, other);
    assert (merge.getOverrides().contains(SettingsType.LOOTRULES));

    merge = new DungeonSettings(other, base);
    assert (!merge.getOverrides().contains(SettingsType.LOOTRULES));
  }

  @Test
  public void overridesCopy() {
    DungeonSettings setting = new DungeonSettings();
    setting.getOverrides().add(SettingsType.LOOTRULES);

    DungeonSettings copy = new DungeonSettings(setting);
    assertTrue(copy.getOverrides().contains(SettingsType.LOOTRULES));
  }

  @Test
  public void testJson() throws Exception {
    JsonObject floor = new JsonObject();
    floor.addProperty("name", "minecraft:dirt");

    JsonObject primary = new JsonObject();
    primary.add("floor", floor);

    JsonArray levels = new JsonArray();
    IntStream.range(0, 5).forEach(levels::add);

    JsonObject theme = new JsonObject();
    theme.add("primary", primary);
    theme.add("level", levels);

    JsonArray themes = new JsonArray();
    themes.add(theme);

    JsonObject dungeonJson = new JsonObject();
    dungeonJson.addProperty("name", "test");

    dungeonJson.add("themes", themes);

    DungeonSettings setting = DungeonSettingsParser.parseDungeonSettings(dungeonJson);

    LevelSettings level = setting.getLevelSettings(0);
    SingleBlockBrush floorBrush = (SingleBlockBrush) level.getTheme().getPrimary().getFloor();
    assertThat(floorBrush.getJson()).isEqualTo(floor);
  }

  @Test
  public void testLootSettingsMerge() {
    DungeonSettings base = new DungeonSettings();
    base.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.SHEARS), 1), 0, 1));

    DungeonSettings other = new DungeonSettings();
    other.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.APPLE), 1), 0, 1));

    DungeonSettings merge = new DungeonSettings(base, other);
    LootRuleManager rules = merge.getLootRules();

    TreasureManager treasure = new TreasureManager(new Random());
    MockChest chest = new MockChest(ChestType.STARTER, 0);
    treasure.addChest(chest);

    rules.process(treasure);

    assert (chest.contains(new ItemStack(Items.APPLE)));
    assert (chest.contains(new ItemStack(Items.SHEARS)));

  }

  @Test
  public void testLootSettingsOverride() {
    DungeonSettings base = new DungeonSettings();
    base.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.SHEARS), 1), 0, 1));

    DungeonSettings other = new DungeonSettings();
    other.getOverrides().add(SettingsType.LOOTRULES);
    other.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.APPLE), 1), 0, 1));

    DungeonSettings merge = new DungeonSettings(base, other);
    LootRuleManager rules = merge.getLootRules();

    TreasureManager treasure = new TreasureManager(new Random());
    MockChest chest = new MockChest(ChestType.STARTER, 0);
    treasure.addChest(chest);

    rules.process(treasure);

    assert (!chest.contains(new ItemStack(Items.SHEARS)));
    assert (chest.contains(new ItemStack(Items.APPLE)));

  }
}
