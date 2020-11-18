package greymerk.roguelike.treasure;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class TreasureManagerTest {

  @Before
  public void setup() {
    Bootstrap.register();
  }

  @Test
  public void addItem() {

    Random rand = new Random();
    WeightedChoice<ItemStack> stick = new WeightedChoice<ItemStack>(new ItemStack(Items.STICK), 1);
    WeightedRandomizer<ItemStack> loot = new WeightedRandomizer<ItemStack>();
    loot.add(stick);

    TreasureManager treasure = new TreasureManager(new Random());
    MockChest toAdd = new MockChest(Treasure.ARMOUR, 0);
    treasure.add(toAdd);
    treasure.addItem(Treasure.ARMOUR, 0, loot, 1);

    treasure.addItem(Treasure.ARMOUR, 1, loot, 1);

    treasure.addItem(Treasure.WEAPONS, 0, loot, 1);

    treasure.addItem(Treasure.WEAPONS, 1, loot, 1);

    treasure.addItem(Treasure.ARMOUR, loot, 1);

    treasure.addItem(Treasure.WEAPONS, loot, 1);

    treasure.addItem(0, loot, 1);

    treasure.addItem(1, loot, 1);

  }
}
