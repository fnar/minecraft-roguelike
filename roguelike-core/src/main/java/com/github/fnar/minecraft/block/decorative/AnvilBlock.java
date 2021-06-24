package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;

public class AnvilBlock extends SingleBlockBrush {

  public enum Damage {
    NEW,
    DAMAGED,
    VERY_DAMAGED,
  }

  private Damage damage = Damage.NEW;

  public AnvilBlock() {
    super(BlockType.ANVIL, Material.METAL);
  }

  public static AnvilBlock anvil() {
    return new AnvilBlock();
  }

  public AnvilBlock setDamage(Damage damage) {
    this.damage = damage;
    return this;
  }

  public Damage getDamage() {
    return damage;
  }

  public AnvilBlock copy() {
    AnvilBlock copy = new AnvilBlock();
    copy.setFacing(getFacing());
    copy.setDamage(damage);
    return copy;
  }
}
