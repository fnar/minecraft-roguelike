package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonObject;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

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
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static greymerk.roguelike.treasure.loot.LootTableRule.newLootTableRule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingsResolverTest {

  private SettingsContainer settingsContainer;
  private TreasureManager treasureManager;
  private SettingsResolver settingsResolver;

  @Mock
  private TreasureChest mockTreasureChest;

  @Captor
  private ArgumentCaptor<ItemStack> itemStackCaptor;

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;

    settingsContainer = new SettingsContainer();
    treasureManager = new TreasureManager(new Random());
    treasureManager.addChest(mockTreasureChest);
    settingsResolver = new SettingsResolver(settingsContainer);

    when(mockTreasureChest.getType()).thenReturn(ChestType.STARTER);
    when(mockTreasureChest.getLevel()).thenReturn(0);
  }

  @Test
  public void ResolveInheritOneLevel() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings toInherit = new DungeonSettings("inherit");

    settingsContainer.put(main, toInherit);

    main.getInherit().add(toInherit.getId());

    toInherit.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.STICK), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Items.STICK);
  }

  @Test
  public void ResolveInheritTwoLevel() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings child = new DungeonSettings("child");
    DungeonSettings grandchild = new DungeonSettings("grandchild");

    settingsContainer.put(main, child, grandchild);

    main.getInherit().add(child.getId());
    child.getInherit().add(grandchild.getId());

    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.STICK), 1), 0, 1));
    grandchild.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.DIAMOND), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Items.DIAMOND, Items.STICK);
  }

  @Test
  public void ResolveInheritTwoLevelMultiple() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings child = new DungeonSettings("child");
    DungeonSettings sibling = new DungeonSettings("sibling");
    DungeonSettings grandchild = new DungeonSettings("grandchild");

    settingsContainer.put(main, child, sibling, grandchild);

    main.getInherit().add(child.getId());
    main.getInherit().add(sibling.getId());
    child.getInherit().add(grandchild.getId());

    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.STICK), 1), 0, 1));
    sibling.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.COAL), 1), 0, 1));
    grandchild.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(new ItemStack(Items.DIAMOND), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Items.COAL, Items.DIAMOND, Items.STICK);
  }

  @Test
  public void processInheritance_BothChildAndParentLootTablesAreKept() {
    ItemStack stick = new ItemStack(Items.STICK);
    ItemStack coal = new ItemStack(Items.COAL);

    DungeonSettings parent = new DungeonSettings("parent");
    parent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(stick, 1), 0, 1));
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(coal, 1), 0, 1));
    child.getInherits().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings assembled = settingsResolver.processInheritance(child);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Items.STICK, Items.COAL);
  }

  @Test
  public void processInheritance_GivesParentLootRulesAndLootTablesToChildren() {
    ItemStack stick = new ItemStack(Items.STICK);
    ItemStack coal = new ItemStack(Items.COAL);

    DungeonSettings parent = new DungeonSettings("parent");
    LootTableRule rewardDungeonLootTable = newLootTableRule(0, "minecraft:dungeon", ChestType.STARTER);
    parent.getLootTables().add(rewardDungeonLootTable);
    parent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(stick, 1), 0, 1));
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(coal, 1), 0, 1));
    child.getInherits().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings assembled = settingsResolver.processInheritance(child);

    assertThat(assembled.getLootTables()).contains(rewardDungeonLootTable);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Items.STICK, Items.COAL);
  }

  @Test
  public void processInheritance_AppliesInheritedThemes() throws Exception {
    String forestThemeSettingsJson = "" +
        "{\n" +
        "  \"name\" : \"theme:forest\",\n" +
        "  \"themes\" : [\n" +
        "    {\n" +
        "      \"base\": \"DARKOAK\",\n" +
        "      \"level\" : 0,\n" +
        "      \"primary\" : {\n" +
        "        \"lightblock\": {\"type\":  \"METABLOCK\", \"data\":  {\"name\":  \"sea_lantern\"}},\n" +
        "        \"walls\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\", \"meta\" : 0}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 1},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 5},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"web\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        },\n" +
        "        \"pillar\" : {\"type\" : \"LAYERS\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 3}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}}\n" +
        "        ]},\n" +
        "        \"door\": {\"name\": \"dark_oak_door\"},\n" +
        "        \"floor\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 75},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 10},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 15},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass\"}, \"weight\" : 20},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass_path\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        }\n" +
        "      }\n" +
        "    }\n" +
        "  ]\n" +
        "}";
    settingsContainer.put(forestThemeSettingsJson);

    String genericDungeonSettingsJson = "" +
        "{\n" +
        "  \"name\": \"dungeon:generic\"" +
        "}";
    settingsContainer.put(genericDungeonSettingsJson);

    String forestTempleId = "dungeon:forest_temple";
    String forestTempleSettingsJson = "" +
        "{\n" +
        "  \"name\" : \"" + forestTempleId + "\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\" : [\n" +
        "    \"dungeon:generic\",\n" +
        "    \"theme:forest\",\n" +
        "    \"dungeon:generic\"\n" +
        "  ]\n" +
        "}";
    settingsContainer.put(forestTempleSettingsJson);
    DungeonSettings forestTempleSettings = settingsContainer.get(new SettingIdentifier(forestTempleId));
    DungeonSettings dungeonSettings = settingsResolver.processInheritance(forestTempleSettings);

    SingleBlockBrush lightBlock = (SingleBlockBrush) dungeonSettings.getLevelSettings(0).getTheme().getPrimary().getLightBlock();
    JsonObject expected = new JsonObject();
    expected.addProperty("name", "sea_lantern");
    assertThat(lightBlock.getJson()).isEqualTo(expected);
  }

  public void assertChestContains(Item... items) {
    verify(mockTreasureChest, times(items.length)).setRandomEmptySlot(itemStackCaptor.capture());
    List<Item> capturedItems = itemStackCaptor.getAllValues().stream().map(ItemStack::getItem).collect(Collectors.toList());
    assertThat(capturedItems.size()).isEqualTo(items.length);
    assertThat(capturedItems).containsExactly(items);
  }

}
