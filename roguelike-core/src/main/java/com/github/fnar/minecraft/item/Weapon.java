package com.github.fnar.minecraft.item;

import java.util.Objects;

import greymerk.roguelike.treasure.loot.Quality;
import lombok.ToString;

@ToString(callSuper = true)
public class Weapon extends RldBaseItem {

  private WeaponType weaponType;
  private Quality quality = Quality.WOOD;

  public Weapon(WeaponType weaponType) {
    this.weaponType = weaponType;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }

  public Weapon withWeaponType(WeaponType weaponType) {
    this.weaponType = weaponType;
    return this;
  }

  public Quality getQuality() {
    return quality;
  }

  public Weapon withQuality(Quality quality) {
    this.quality = quality;
    return this;
  }

  public Weapon wooden() {
    withQuality(Quality.WOOD);
    return this;
  }

  public Weapon stone() {
    withQuality(Quality.STONE);
    return this;
  }

  public Weapon iron() {
    withQuality(Quality.IRON);
    return this;
  }

  public Weapon golden() {
    withQuality(Quality.GOLD);
    return this;
  }

  public Weapon diamond() {
    withQuality(Quality.DIAMOND);
    return this;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.WEAPON;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Weapon weapon = (Weapon) o;
    return weaponType == weapon.weaponType && quality == weapon.quality;
  }

  @Override
  public int hashCode() {
    return Objects.hash(weaponType, quality);
  }
}
