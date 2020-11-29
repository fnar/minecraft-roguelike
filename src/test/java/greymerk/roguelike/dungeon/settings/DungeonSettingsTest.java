package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.MockChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.blocks.BlockType;

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

    DungeonSettings setting;

    JsonObject json = new JsonObject();
    json.addProperty("name", "test");

    JsonObject theme = new JsonObject();


    JsonArray levels = new JsonArray();
    for (int i = 0; i < 5; ++i) {
      levels.add(i);
    }

    theme.add("level", levels);

    JsonObject primary = new JsonObject();
    theme.add("primary", primary);

    JsonObject floor = new JsonObject();
    primary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    JsonArray themes = new JsonArray();
    themes.add(theme);
    json.add("themes", themes);

    setting = DungeonSettingsParser.parseDungeonSettings(json);

    LevelSettings level = setting.getLevelSettings(0);
    ThemeBase t = level.getTheme();
    BlockSet bs = t.getPrimary();
    IBlockFactory f = bs.getFloor();
    assert (f.equals(BlockType.get(BlockType.DIRT)));

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
