package greymerk.roguelike.treasure;

import com.google.common.base.Predicates;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.IWeighted;

import static java.util.stream.Collectors.toList;

public class TreasureManager {

  List<TreasureChest> chests = new ArrayList<>();
  private Random random;

  public TreasureManager(Random random) {
    this.random = random;
  }

  public void addChest(TreasureChest toAdd) {
    this.chests.add(toAdd);
  }

  public void addItemToAll(Predicate<TreasureChest> chestPredicate, IWeighted<ItemStack> items, int amount) {
    findChests(chestPredicate).forEach(chest ->
        IntStream.range(0, amount)
            .mapToObj(i -> items.get(random))
            .forEach(chest::setRandomEmptySlot));
  }

  public void addItem(Predicate<TreasureChest> chestPredicate, IWeighted<ItemStack> items, int amount) {
    List<TreasureChest> chests = findChests(chestPredicate);
    if (chests.isEmpty()) {
      return;
    }

    IntStream.range(0, amount)
        .mapToObj(i -> items.get(random))
        .forEach(item -> addItemToRandomChest(chests, item));
  }

  private void addItemToRandomChest(List<TreasureChest> chests, ItemStack item) {
    chests.get(random.nextInt(chests.size())).setRandomEmptySlot(item);
  }

  public List<TreasureChest> findChests(Predicate<TreasureChest> predicate) {
    return this.chests.stream()
        .filter(predicate)
        .collect(toList());
  }

  public static Predicate<TreasureChest> ofType(ChestType chestType) {
    return chest -> chest.getType().equals(chestType);
  }

  public static Predicate<TreasureChest> ofTypeOnLevel(ChestType chestType, int level) {
    return Predicates.and(
        chest -> chest.getLevel() == level,
        chest -> chest.getType().equals(chestType));
  }

  public static Predicate<TreasureChest> onLevel(int level) {
    return Predicates.and(
        TreasureChest::isNotEmpty,
        chest -> chest.getLevel() == level);
  }
}
