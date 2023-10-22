package com.github.fnar.minecraft.item;

public class Miscellaneous extends RldBaseItem {

  private final Type type;

  public Miscellaneous(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.MISCELLANEOUS;
  }

  public enum Type {
    BOOK,
    ENCHANTED_BOOK,
    ENDER_EYE,
    ENDER_PEARL,
    EXPERIENCE_BOTTLE,
    GLASS_BOTTLE,
    LEAD,
    SADDLE,
    SIGN
    ;

    public Miscellaneous asItem() {
      return new Miscellaneous(this);
    }
  }
}
