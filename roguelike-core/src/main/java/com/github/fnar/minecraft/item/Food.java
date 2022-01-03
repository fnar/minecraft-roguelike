package com.github.fnar.minecraft.item;

import java.util.Objects;

public class Food extends RldBaseItem {

  private final Type type;

  public Food(Type type) {
    this.type = type;
  }

  public Type getFoodType() {
    return type;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.FOOD;
  }

  public enum Type {
    APPLE,
    BAKED_POTATO,
    BREAD,
    CARROT,
    COOKED_BEEF,
    COOKED_CHICKEN,
    COOKED_FISH,
    COOKED_MUTTON,
    COOKED_PORKCHOP,
    COOKIE,
    GOLDEN_APPLE,
    GOLDEN_CARROT,
    MILK_BUCKET,
    MUSHROOM_STEW,
    RAW_COD,
    ROTTEN_FLESH,
    POTATO;

    public Food asItem() {
      return new Food(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Food food = (Food) o;
    return type == food.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
