package com.github.fnar.minecraft.item;

public class Ingredient extends RldBaseItem {

  private final Type type;

  public Ingredient(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.INGREDIENT;
  }

  public enum Type {
    BLAZE_POWDER,
    BROWN_MUSHROOM,
    FERMENTED_SPIDER_EYE,
    GHAST_TEAR,
    GLASS_BOTTLE,
    GLISTERING_MELON_SLICE,
    GLOWSTONE_DUST,
    GUNPOWDER,
    MAGMA_CREAM,
    NETHER_WART,
    PUFFERFISH,
    RABBIT_FOOT,
    REDSTONE,
    RED_MUSHROOM,
    SPIDER_EYE,
    SUGAR,
    ;

    public Ingredient asItem() {
      return new Ingredient(this);
    }

    public RldItemStack asItemStack() {
      return asItem().asStack();
    }
  }

}
