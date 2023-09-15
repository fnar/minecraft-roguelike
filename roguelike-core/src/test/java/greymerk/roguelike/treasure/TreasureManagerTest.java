package greymerk.roguelike.treasure;

import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static greymerk.roguelike.treasure.TreasureManager.ofType;
import static greymerk.roguelike.treasure.TreasureManager.ofTypeOnLevel;
import static greymerk.roguelike.treasure.TreasureManager.onLevelAndNotEmpty;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TreasureManagerTest {

  @Mock
  private TreasureChest mockTreasureChest;

  @Test
  public void addItem() {
    WeightedChoice<RldItemStack> stick = new WeightedChoice<>(Material.Type.STICK.asItem().asStack(), 1);
    WeightedRandomizer<RldItemStack> loot = new WeightedRandomizer<>();
    loot.add(stick);

    TreasureManager treasure = new TreasureManager(new Random());
    when(mockTreasureChest.getType()).thenReturn(ChestType.ARMOUR);
    when(mockTreasureChest.getLevel()).thenReturn(0);

    treasure.addChest(mockTreasureChest);
    treasure.addItem(ofTypeOnLevel(ChestType.ARMOUR, 0), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.ARMOUR, 1), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.WEAPONS, 0), loot, 1);
    treasure.addItem(ofTypeOnLevel(ChestType.WEAPONS, 1), loot, 1);
    treasure.addItem(ofType(ChestType.WEAPONS), loot, 1);
    treasure.addItem(ofType(ChestType.WEAPONS), loot, 1);
    treasure.addItem(onLevelAndNotEmpty(0), loot, 1);
    treasure.addItem(onLevelAndNotEmpty(1), loot, 1);
  }
}
