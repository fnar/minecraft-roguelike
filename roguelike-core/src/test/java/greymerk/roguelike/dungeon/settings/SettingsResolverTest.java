package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonObject;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.item.ItemType;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.dungeon.settings.fixture.Dungeon;
import com.github.fnar.roguelike.dungeon.settings.fixture.Theme;

import net.minecraft.init.Bootstrap;

import org.assertj.core.util.Lists;
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

  @Mock
  private ModLoader modLoader;

  @Captor
  private ArgumentCaptor<RldItemStack> itemStackCaptor;

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;

    settingsContainer = new SettingsContainer(modLoader);
    treasureManager = new TreasureManager(new Random());
    treasureManager.addChest(mockTreasureChest);
    settingsResolver = new SettingsResolver(settingsContainer);

    when(mockTreasureChest.getType()).thenReturn(ChestType.STARTER);
    when(mockTreasureChest.getLevel()).thenReturn(0);
    when(mockTreasureChest.isNotEmpty()).thenReturn(true);
  }

  @Test
  public void ResolveInheritOneLevel() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings toInherit = new DungeonSettings("inherit");

    settingsContainer.put(main, toInherit);

    main.getInherit().add(toInherit.getId());

    toInherit.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Material.Type.STICK.asItem());
  }

  @Test
  public void ResolveInheritTwoLevel() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings child = new DungeonSettings("child");
    DungeonSettings grandchild = new DungeonSettings("grandchild");

    settingsContainer.put(main, child, grandchild);

    main.getInherit().add(child.getId());
    child.getInherit().add(grandchild.getId());

    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1), 0, 1));
    grandchild.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.DIAMOND.asItem().asStack(), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Material.Type.DIAMOND.asItem(), Material.Type.STICK.asItem());
  }

  @Test
  public void ResolveInheritTwoLevelMultiple() {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings parent = new DungeonSettings("parent");
    DungeonSettings otherParent = new DungeonSettings("otherParent");
    DungeonSettings grandparent = new DungeonSettings("grandparent");

    settingsContainer.put(main, parent, otherParent, grandparent);

    main.getInherit().add(parent.getId());
    main.getInherit().add(otherParent.getId());
    parent.getInherit().add(grandparent.getId());

    parent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1), 0, 1));
    otherParent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.COAL.asItem().asStack(), 1), 0, 1));
    grandparent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Material.Type.DIAMOND.asItem().asStack(), 1), 0, 1));

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Material.Type.COAL.asItem(), Material.Type.DIAMOND.asItem(), Material.Type.STICK.asItem());
  }

  @Test
  public void processInheritance_BothChildAndParentLootTablesAreKept() {
    RldItemStack stick = Material.Type.STICK.asItem().asStack();
    RldItemStack coal = Material.Type.COAL.asItem().asStack();

    DungeonSettings parent = new DungeonSettings("parent");
    parent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(stick, 1), 0, 1));
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(coal, 1), 0, 1));
    child.getInherit().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings assembled = settingsResolver.processInheritance(child);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Material.Type.STICK.asItem(), Material.Type.COAL.asItem());
  }

  @Test
  public void processInheritance_GivesParentLootRulesAndLootTablesToChildren() {
    RldItemStack stick = Material.Type.STICK.asItem().asStack();
    RldItemStack coal = Material.Type.COAL.asItem().asStack();

    DungeonSettings parent = new DungeonSettings("parent");
    LootTableRule rewardDungeonLootTable = newLootTableRule(0, "minecraft:dungeon", ChestType.STARTER);
    parent.getLootTables().add(rewardDungeonLootTable);
    parent.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(stick, 1), 0, 1));
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(coal, 1), 0, 1));
    child.getInherit().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings assembled = settingsResolver.processInheritance(child);

    assertThat(assembled.getLootTables()).contains(rewardDungeonLootTable);

    assertChestContains();
    assembled.getLootRules().process(treasureManager);
    assertChestContains(Material.Type.STICK.asItem(), Material.Type.COAL.asItem());
  }

  @Test
  public void processInheritance_AppliesInheritedThemes() throws Exception {
    settingsContainer.put(Dungeon.dungeonGenericSettingsJson());
    settingsContainer.put(Theme.forestThemeSettingsJson());
    settingsContainer.put(Dungeon.dungeonForestTempleSettingsJson());

    String forestTempleId = "dungeon:forest_temple";
    DungeonSettings forestTempleSettings = settingsContainer.get(new SettingIdentifier(forestTempleId));
    DungeonSettings dungeonSettings = settingsResolver.processInheritance(forestTempleSettings);

    SingleBlockBrush lightBlock = (SingleBlockBrush) dungeonSettings.getLevelSettings(0).getTheme().getPrimary().getLightBlock();
    JsonObject expected = new JsonObject();
    expected.addProperty("name", "sea_lantern");
    assertThat(lightBlock.getJson()).isEqualTo(expected);
  }

  public void assertChestContains(RldItem... items) {
    verify(mockTreasureChest, times(items.length)).setRandomEmptySlot(itemStackCaptor.capture());
    List<RldItem> capturedItems = itemStackCaptor.getAllValues().stream().map(RldItemStack::getItem).collect(Collectors.toList());
    assertThat(capturedItems.size()).isEqualTo(items.length);
    assertThat(capturedItems).containsExactlyInAnyOrder(items);
  }

  @Test
  public void potions() throws Exception {
    List<String> potionTypes = Lists.newArrayList(
        "mundane",
        "thick",
        "night_vision",
        "long_night_vision",
        "invisibility",
        "long_invisibility",
        "leaping",
        "strong_leaping",
        "long_leaping",
        "fire_resistance",
        "long_fire_resistance",
        "swiftness",
        "strong_swiftness",
        "long_swiftness",
        "slowness",
        "strong_slowness",
        "long_slowness",
        "water_breathing",
        "long_water_breathing",
        "healing",
        "strong_healing",
        "long_healing",
        "harming",
        "strong_harming",
        "poison",
        "strong_poison",
        "long_poison",
        "regeneration",
        "strong_regeneration",
        "long_regeneration",
        "strength",
        "strong_strength",
        "long_strength",
        "weakness",
        "long_weakness",
        "luck",
        "turtle_master",
        "strong_turtle_master",
        "long_turtle_master",
        "slow_falling",
        "long_slow_falling"
    );
    String potionLootRules = potionTypes.stream().map(this::createPotionLootRule).collect(Collectors.joining(","));
    String settingsName = "loot:potions";
    String potionLootSettings = "" +
        "{\n" +
        "  \"name\": \"" + settingsName +"\",\n" +
        "  \"lootRules\": [" + potionLootRules +"]" +
        "}";

    settingsContainer.put(potionLootSettings);
    DungeonSettings dungeonSettings = settingsResolver.getByName(settingsName);

    dungeonSettings.getLootRules().process(treasureManager);

    verify(mockTreasureChest, times(potionTypes.size())).setRandomEmptySlot(itemStackCaptor.capture());

    List<RldItemStack> potionItemStacks = itemStackCaptor.getAllValues();
    for (RldItemStack potionItemStack : potionItemStacks) {
      assertThat(potionItemStack.getItem().getItemType()).isEqualTo(ItemType.POTION);
    }

  }

  private String createPotionLootRule(String potion) {
    return createLootRule("potion", potion);
  }

  private String createMixtureLootRule(String mixture) {
    return createLootRule("mixture", mixture);
  }

  private String createLootRule(final String type, String potion) {
    return "{\"level\": [0, 1, 2, 3, 4], \"each\": true, \"quantity\": 1, \"loot\": [{\"data\": {\"type\": \"" + type + "\", \"name\": \"" + potion + "\", \"min\": 1, \"max\":1}}]}";
  }

}
