package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.util.IWeighted;

import static java.lang.Integer.compare;


public class DungeonWeightedChoice implements IWeighted<DungeonRoom>, Comparable<IWeighted<?>> {

  DungeonRoom type;
  int chance;

  public DungeonWeightedChoice(DungeonRoom type, int chance) {
    this.type = type;
    this.chance = chance;
  }

  public boolean choose(Random rand) {
    return rand.nextInt(chance) == 0;
  }

  public IDungeonRoom getInstance() {
    return type.instantiate();
  }


  @Override
  public int compareTo(IWeighted<?> other) {
    return compare(chance, other.getWeight());
  }

  @Override
  public int getWeight() {
    return chance;
  }

  @Override
  public DungeonRoom get(Random rand) {
    return type;
  }

}
