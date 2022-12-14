package greymerk.roguelike.treasure;

import com.github.fnar.minecraft.item.RldItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.IWeighted;

import static java.util.stream.Collectors.toList;

public class TreasureManager {

  Set<TreasureChest> chests = new HashSet<>();
  private final Random random;

  public TreasureManager(Random random) {
    this.random = random;
  }

  public void addChest(TreasureChest toAdd) {
    this.chests.add(toAdd);
  }

  public void addItemToAll(Predicate<TreasureChest> chestPredicate, IWeighted<RldItemStack> items, int amount) {
    findChests(chestPredicate).forEach(chest ->
        IntStream.range(0, amount)
            .mapToObj(i -> items.get(random))
            .forEach(chest::setRandomEmptySlot));
  }

  public void addItem(Predicate<TreasureChest> chestPredicate, IWeighted<RldItemStack> items, int amount) {
    List<TreasureChest> chests = findChests(chestPredicate);
    if (chests.isEmpty()) {
      return;
    }

    IntStream.range(0, amount)
        .mapToObj(i -> items.get(random))
        .forEach(item -> addItemToRandomChest(chests, item));
  }

  private void addItemToRandomChest(List<TreasureChest> chests, RldItemStack item) {
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
    return isOnLevel(level).and(ofType(chestType));
  }

  private static Predicate<TreasureChest> isOnLevel(int level) {
    return chest -> chest.getLevel() == level;
  }

  public static Predicate<TreasureChest> onLevelAndNotEmpty(int level) {
    return ((Predicate<TreasureChest>) TreasureChest::isNotEmpty).and(isOnLevel(level));
  }
}
