package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.Food;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.ToolType;

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

import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LootRuleManagerTest {

  @Mock
  private TreasureChest mockTreasureChest;

  @Captor
  private ArgumentCaptor<RldItemStack> itemStackCaptor;

  @Before
  public void setUp() {
    when(mockTreasureChest.getType()).thenReturn(ChestType.STARTER);
    when(mockTreasureChest.getLevel()).thenReturn(0);
  }

  @Test
  public void testAdd() {
    LootRuleManager manager = new LootRuleManager();
    manager.add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(ToolType.SHEARS.asItem().asStack(), 1), 0, 1));
    TreasureManager treasure = new TreasureManager(new Random());

    treasure.addChest(mockTreasureChest);

    manager.process(treasure);

    assertChestContains(ToolType.SHEARS.asItem());
  }

  @Test
  public void testMerge() {
    LootRuleManager base = new LootRuleManager();
    base.add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(ToolType.SHEARS.asItem().asStack(), 1), 0, 1));

    LootRuleManager other = new LootRuleManager();
    other.add(new TypedForEachLootRule(ChestType.STARTER, new WeightedChoice<>(Food.Type.APPLE.asItem().asStack(), 1), 0, 1));

    base.merge(other);

    TreasureManager treasure = new TreasureManager(new Random());
    treasure.addChest(mockTreasureChest);

    base.process(treasure);

    assertChestContains(ToolType.SHEARS.asItem(), Food.Type.APPLE.asItem());
  }

  public void assertChestContains(RldItem... items) {
    verify(mockTreasureChest, times(items.length)).setRandomEmptySlot(itemStackCaptor.capture());
    List<RldItem> capturedItems = itemStackCaptor.getAllValues().stream().map(RldItemStack::getItem).collect(Collectors.toList());
    assertThat(capturedItems.size()).isEqualTo(items.length);
    assertThat(capturedItems).containsExactly(items);
  }

}
