package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.util.IWeighted;

import static java.util.stream.Collectors.*;

public class TreasureManager {

  List<ITreasureChest> chests = new ArrayList<>();

  public void add(ITreasureChest toAdd) {
    this.chests.add(toAdd);
  }

  public void addItemToAll(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount) {
    addItemToAll(rand, this.getChests(type, level), item, amount);
  }

  public void addItemToAll(Random rand, int level, IWeighted<ItemStack> item, int amount) {
    addItemToAll(rand, this.getChests(level), item, amount);
  }

  public void addItemToAll(Random rand, Treasure type, IWeighted<ItemStack> item, int amount) {
    addItemToAll(rand, this.getChests(type), item, amount);
  }

  private void addItemToAll(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    chests.forEach(chest ->
        IntStream.range(0, amount)
            .mapToObj(i -> item.get(rand))
            .forEach(chest::setRandomEmptySlot));
  }


  public void addItem(Random rand, int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(rand, getChests(level), item, amount);
  }

  public void addItem(Random rand, Treasure type, IWeighted<ItemStack> item, int amount) {
    this.addItem(rand, getChests(type), item, amount);
  }

  public void addItem(Random rand, Treasure type, int level, IWeighted<ItemStack> item, int amount) {
    this.addItem(rand, getChests(type, level), item, amount);
  }

  private void addItem(Random rand, List<ITreasureChest> chests, IWeighted<ItemStack> item, int amount) {
    if (chests.isEmpty()) {
      return;
    }

    IntStream.range(0, amount)
        .mapToObj(i -> chests.get(rand.nextInt(chests.size())))
        .forEach(chest -> chest.setRandomEmptySlot(item.get(rand)));
  }

  public List<ITreasureChest> getChests(Treasure type, int level) {
    return this.chests.stream()
        .filter(chest -> chest.isType(type))
        .filter(chest -> chest.isOnLevel(level))
        .collect(toList());
  }

  public List<ITreasureChest> getChests(Treasure type) {
    return this.chests.stream()
        .filter(chest -> chest.isType(type))
        .collect(toList());
  }

  public List<ITreasureChest> getChests(int level) {
    return this.chests.stream()
        .filter(ITreasureChest::isNotEmpty)
        .filter(chest -> chest.isOnLevel(level))
        .collect(toList());
  }

  public List<ITreasureChest> getChests() {
    return this.chests.stream()
        .filter(ITreasureChest::isNotEmpty)
        .collect(toList());
  }
}
