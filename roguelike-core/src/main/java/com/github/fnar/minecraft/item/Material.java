package com.github.fnar.minecraft.item;

import java.util.Objects;

public class Material extends RldBaseItem {

  private final Type type;

  public Material(Type type) {
    this.type = type;
  }

  public static Material clayBall() {
    return new Material(Type.CLAY_BALL);
  }

  @Override
  public ItemType getItemType() {
    return ItemType.MATERIAL;
  }

  public Type getMaterialType() {
    return type;
  }

  public enum Type {
    BONE,
    BOOK,
    CLAY_BALL,
    COAL,
    DIAMOND,
    EMERALD,
    FEATHER,
    FLINT,
    GOLD_INGOT,
    GOLD_NUGGET,
    IRON_INGOT,
    IRON_NUGGET,
    LEATHER,
    PAPER,
    SLIME_BALL,
    SNOWBALL,
    STICK,
    STRING,
    WHEAT;

    public Material asItem() {
      return new Material(this);
    }

    public RldItemStack asItemStack() {
      return asItem().asStack();
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
    Material material = (Material) o;
    return type == material.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
