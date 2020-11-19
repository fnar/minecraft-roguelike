package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.treasure.MockChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.rule.TypedForEachLootRule;
import greymerk.roguelike.util.WeightedChoice;

public class LootRuleManagerTest {

  @Before
  public void setUp() {
    Bootstrap.register();
  }

  @Test
  public void testAdd() {
    LootRuleManager manager = new LootRuleManager();
    manager.add(new TypedForEachLootRule(Treasure.STARTER, new WeightedChoice<>(new ItemStack(Items.SHEARS), 1), 0, 1));
    TreasureManager treasure = new TreasureManager(new Random());

    MockChest chest = new MockChest(Treasure.STARTER, 0);
    treasure.addChest(chest);

    manager.process(treasure);

    assert (!chest.contains(new ItemStack(Items.APPLE)));
    assert (chest.contains(new ItemStack(Items.SHEARS)));

  }

  @Test
  public void testMerge() {

    LootRuleManager base = new LootRuleManager();
    base.add(new TypedForEachLootRule(Treasure.STARTER, new WeightedChoice<>(new ItemStack(Items.SHEARS), 1), 0, 1));

    LootRuleManager other = new LootRuleManager();
    other.add(new TypedForEachLootRule(Treasure.STARTER, new WeightedChoice<>(new ItemStack(Items.APPLE), 1), 0, 1));

    base.merge(other);

    TreasureManager treasure = new TreasureManager(new Random());
    MockChest chest = new MockChest(Treasure.STARTER, 0);
    treasure.addChest(chest);

    base.process(treasure);

    assert (chest.contains(new ItemStack(Items.APPLE)));
    assert (chest.contains(new ItemStack(Items.SHEARS)));
  }

}
