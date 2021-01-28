package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DungeonSettingsTest {

  @Mock
  private TreasureChest mockTreasureChest;

  @Captor
  private ArgumentCaptor<ItemStack> itemStackCaptor;

  @Before
  public void before() {
    RogueConfig.testing = true;
    Bootstrap.register();

    when(mockTreasureChest.getType()).thenReturn(ChestType.STARTER);
    when(mockTreasureChest.getLevel()).thenReturn(0);
  }

  @Test
  public void overridesAreNotInherited() {
    DungeonSettings parent0 = new DungeonSettings();
    DungeonSettings parent1 = new DungeonSettings();
    parent1.getOverrides().add(SettingsType.LOOTRULES);
    assertThat(parent1.getOverrides()).contains(SettingsType.LOOTRULES);

    DungeonSettings child = parent1.inherit(parent0);
    assertThat(child.getOverrides()).isEmpty();

    child = parent0.inherit(parent1);
    assertThat(child.getOverrides()).isEmpty();
  }

  @Test
  public void overrides_onlyPassesOnTheBaseParentsLootRules() {
    SingleUseLootRule stickLootRule = new SingleUseLootRule(new WeightedChoice<>(new ItemStack(Items.STICK), 1), 0, 1);
    SingleUseLootRule boneLootRule = new SingleUseLootRule(new WeightedChoice<>(new ItemStack(Items.BONE), 1), 0, 1);

    DungeonSettings parent0 = new DungeonSettings();
    parent0.getLootRules().add(stickLootRule);
    parent0.getOverrides().add(SettingsType.LOOTRULES);

    DungeonSettings parent1 = new DungeonSettings();
    parent1.getLootRules().add(boneLootRule);

    assertThat(parent0.inherit(parent1).getLootRules().getRules()).containsOnly(stickLootRule);
  }

  @Test
  public void overrides_onlyDiscardsIfTheBaseParentIsTheOverrider() {
    SingleUseLootRule stickLootRule = new SingleUseLootRule(new WeightedChoice<>(new ItemStack(Items.STICK), 1), 0, 1);
    SingleUseLootRule boneLootRule = new SingleUseLootRule(new WeightedChoice<>(new ItemStack(Items.BONE), 1), 0, 1);

    DungeonSettings parent0 = new DungeonSettings();
    parent0.getLootRules().add(stickLootRule);

    DungeonSettings parent1 = new DungeonSettings();
    parent1.getLootRules().add(boneLootRule);
    parent1.getOverrides().add(SettingsType.LOOTRULES);

    assertThat(parent0.inherit(parent1).getLootRules().getRules()).containsOnly(stickLootRule, boneLootRule);
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

    Optional<DungeonSettings> dungeonSettings = DungeonSettingsParser.parseDungeonSettings(dungeonJson);
    if (!dungeonSettings.isPresent()) {
      fail("Expected DungeonSettings to be found but it was not.");
    }
    DungeonSettings setting = (DungeonSettings) dungeonSettings.get();

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

    DungeonSettings merge = other.inherit(base);
    LootRuleManager rules = merge.getLootRules();

    TreasureManager treasure = new TreasureManager(new Random());

    treasure.addChest(mockTreasureChest);

    rules.process(treasure);

    assertChestContains(Items.SHEARS, Items.APPLE);
  }

  @Test
  public void testLootSettingsOverride() {
    DungeonSettings base = new DungeonSettings();
    base.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.SHEARS), 1), 0, 1));

    DungeonSettings other = new DungeonSettings();
    other.getOverrides().add(SettingsType.LOOTRULES);
    other.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.APPLE), 1), 0, 1));

    DungeonSettings merge = other.inherit(base);
    LootRuleManager rules = merge.getLootRules();

    TreasureManager treasure = new TreasureManager(new Random());
    treasure.addChest(mockTreasureChest);

    rules.process(treasure);

    assertChestContains(Items.APPLE);
  }

  public void assertChestContains(Item... items) {
    verify(mockTreasureChest, times(items.length)).setRandomEmptySlot(itemStackCaptor.capture());
    List<Item> capturedItems = itemStackCaptor.getAllValues().stream().map(ItemStack::getItem).collect(Collectors.toList());
    assertThat(capturedItems.size()).isEqualTo(items.length);
    assertThat(capturedItems).containsExactlyInAnyOrder(items);
  }

  @Test
  public void toStringTest() {
    String foo = new DungeonSettings("foo").toString();
    System.out.println(foo);
  }
}
