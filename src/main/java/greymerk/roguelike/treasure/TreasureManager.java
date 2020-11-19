package greymerk.roguelike.treasure;

import com.google.common.base.Predicates;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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

  public void addItemToAll(Treasure type, int level, IWeighted<ItemStack> item, int amount) {
    addItemToAll(this.findChests(ofTypeOnLevel(type, level)), item, amount);
  }

  public void addItemToAll(int level, IWeighted<ItemStack> item, int amount) {
    addItemToAll(findChests(onLevel(level)), item, amount);
  }

  private void addItemToAll(List<TreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    chests.forEach(chest ->
        IntStream.range(0, amount)
            .mapToObj(i -> item.get(this.random))
            .forEach(chest::setRandomEmptySlot));
  }

  public void addItem(int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(onLevel(level)), item, amount);
  }

  public void addItem(Treasure type, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(ofType(type)), item, amount);
  }

  public void addItem(Treasure type, int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(ofTypeOnLevel(type, level)), item, amount);
  }

  private void addItem(List<TreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    if (chests.isEmpty()) {
      return;
    }

    IntStream.range(0, amount)
        .mapToObj(i -> chests.get(this.random.nextInt(chests.size())))
        .forEach(chest -> chest.setRandomEmptySlot(item.get(this.random)));
  }

  public List<TreasureChest> findChests(Predicate<TreasureChest> predicate) {
    return this.chests.stream()
        .filter(predicate)
        .collect(toList());
  }

  public Predicate<TreasureChest> ofType(Treasure type) {
    return chest -> chest.isType(type);
  }

  public Predicate<TreasureChest> ofTypeOnLevel(Treasure type, int level) {
    return Predicates.and(
        chest -> chest.isOnLevel(level),
        chest -> chest.isType(type));
  }

  public Predicate<TreasureChest> onLevel(int level) {
    return Predicates.and(
        TreasureChest::isNotEmpty,
        chest -> chest.isOnLevel(level));
  }
}
