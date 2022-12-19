package com.github.fnar.minecraft.item;

import java.util.Objects;

import greymerk.roguelike.treasure.loot.Quality;
import lombok.ToString;

@ToString(callSuper = true)
public class Armour extends RldBaseItem {

  private final ArmourType armourType;
  private Quality quality = Quality.WOOD;

  public Armour(ArmourType armourType) {
    this.armourType = armourType;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.ARMOUR;
  }

  public ArmourType getArmourType() {
    return armourType;
  }

  public Quality getQuality() {
    return quality;
  }

  public Armour withQuality(Quality quality) {
    this.quality = quality;
    return this;
  }

  public Armour leather() {
    withQuality(Quality.WOOD);
    return this;
  }

  public Armour chainmail() {
    withQuality(Quality.STONE);
    return this;
  }

  public Armour iron() {
    withQuality(Quality.IRON);
    return this;
  }

  public Armour golden() {
    withQuality(Quality.GOLD);
    return this;
  }

  public Armour diamond() {
    withQuality(Quality.DIAMOND);
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
    if (!super.equals(o)) {
      return false;
    }
    Armour armour = (Armour) o;
    return armourType == armour.armourType && quality == armour.quality;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), armourType, quality);
  }
}
