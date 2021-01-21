package greymerk.roguelike.util;

import java.util.Objects;
import java.util.Random;

import lombok.ToString;

@ToString
public class WeightedChoice<T> implements IWeighted<T> {

  private final T item;
  private final int weight;

  public WeightedChoice(T toAdd, int weight) {
    item = toAdd;
    this.weight = weight;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public T get(Random rand) {
    return item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WeightedChoice<?> that = (WeightedChoice<?>) o;
    return weight == that.weight &&
        Objects.equals(item, that.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, weight);
  }
}
