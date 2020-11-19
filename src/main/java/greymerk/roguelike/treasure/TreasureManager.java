package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.util.IWeighted;

import static java.util.stream.Collectors.*;

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
    addItemToAll(this.findChests(type, level), item, amount);
  }

  public void addItemToAll(int level, IWeighted<ItemStack> item, int amount) {
    addItemToAll(this.findChests(level), item, amount);
  }

  private void addItemToAll(List<TreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    chests.forEach(chest ->
        IntStream.range(0, amount)
            .mapToObj(i -> item.get(this.random))
            .forEach(chest::setRandomEmptySlot));
  }

  public void addItem(int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(level), item, amount);
  }

  public void addItem(Treasure type, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(type), item, amount);
  }

  public void addItem(Treasure type, int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(findChests(type, level), item, amount);
  }

  private void addItem(List<TreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    if (chests.isEmpty()) {
      return;
    }

    IntStream.range(0, amount)
        .mapToObj(i -> chests.get(this.random.nextInt(chests.size())))
        .forEach(chest -> chest.setRandomEmptySlot(item.get(this.random)));
  }

  public List<TreasureChest> findChests(Treasure type, int level) {
    return this.chests.stream()
        .filter(chest -> chest.isOnLevel(level))
        .filter(chest -> chest.isType(type))
        .collect(toList());
  }

  private List<TreasureChest> findChests(Treasure type) {
    return this.chests.stream()
        .filter(chest -> chest.isType(type))
        .collect(toList());
  }

  private List<TreasureChest> findChests(int level) {
    return this.chests.stream()
        .filter(TreasureChest::isNotEmpty)
        .filter(chest -> chest.isOnLevel(level))
        .collect(toList());
  }

  public List<TreasureChest> findChests() {
    return this.chests.stream()
        .filter(TreasureChest::isNotEmpty)
        .collect(toList());
  }
}
