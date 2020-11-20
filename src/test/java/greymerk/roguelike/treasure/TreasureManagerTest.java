package greymerk.roguelike.treasure;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.ofType;
import static greymerk.roguelike.treasure.TreasureManager.ofTypeOnLevel;
import static greymerk.roguelike.treasure.TreasureManager.onLevel;

public class TreasureManagerTest {

  @Before
  public void setup() {
    Bootstrap.register();
  }

  @Test
  public void addItem() {
    WeightedChoice<ItemStack> stick = new WeightedChoice<>(new ItemStack(Items.STICK), 1);
    WeightedRandomizer<ItemStack> loot = new WeightedRandomizer<>();
    loot.add(stick);

    TreasureManager treasure = new TreasureManager(new Random());
    MockChest toAdd = new MockChest(ChestType.ARMOUR, 0);
    treasure.addChest(toAdd);
    treasure.addItem(ofTypeOnLevel(ChestType.ARMOUR, 0), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.ARMOUR, 1), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.WEAPONS, 0), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.WEAPONS, 1), loot, 1);
    treasure.addItem(ofType(ChestType.WEAPONS), loot, 1);
    treasure.addItem(ofType(ChestType.WEAPONS), loot, 1);
    treasure.addItem(onLevel(0), loot, 1);
    treasure.addItem(onLevel(1), loot, 1);
  }
}
