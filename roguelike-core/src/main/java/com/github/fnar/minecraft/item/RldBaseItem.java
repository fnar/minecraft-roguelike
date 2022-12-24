package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import com.github.fnar.util.Color;

import java.util.List;
import java.util.Objects;

import lombok.ToString;

@ToString
public abstract class RldBaseItem implements RldItem {

  private Color color;
  private final List<Enchantment> enchantments = Lists.newArrayList();
  private int plzEnchantLevel = 0;

  public void addEnchantment(Enchantment enchantment) {
    this.enchantments.add(enchantment);
  }

  public RldBaseItem withEnchantment(Enchantment enchantment) {
    addEnchantment(enchantment);
    return this;
  }

  public List<Enchantment> getEnchantments() {
    return enchantments;
  }

  public boolean isPlzEnchant() {
    return plzEnchantLevel != 0;
  }

  public int getPlzEnchantLevel() {
    return plzEnchantLevel;
  }

  public RldBaseItem plzEnchantAtLevel(int level) {
    this.plzEnchantLevel = level;
    return this;
  }

  public Color getColor() {
    return color;
  }

  public RldBaseItem withColor(Color color) {
    this.color = color;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RldBaseItem that = (RldBaseItem) o;
    return plzEnchantLevel == that.plzEnchantLevel && color.equals(that.color) && enchantments.equals(that.enchantments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, enchantments, plzEnchantLevel);
  }
}
