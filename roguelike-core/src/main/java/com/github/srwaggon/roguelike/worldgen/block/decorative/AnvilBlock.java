package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.Material;

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
}
