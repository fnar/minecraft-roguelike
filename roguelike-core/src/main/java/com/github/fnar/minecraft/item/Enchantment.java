package com.github.fnar.minecraft.item;

public class Enchantment {

  private Effect effect;
  private int level = 1;

  public Effect getEnchant() {
    return effect;
  }

  public Enchantment withEffect(Effect effect) {
    this.effect = effect;
    return this;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Enchantment withLevel(int level) {
    setLevel(level);
    return this;
  }

  public enum Effect {

    AQUA_AFFINITY,
    BANE_OF_ARTHROPODS,
    BLAST_PROTECTION,
    DEPTH_STRIDER,
    EFFICIENCY,
    FEATHER_FALLING,
    FIRE_ASPECT,
    FIRE_PROTECTION,
    FLAME,
    FORTUNE,
    INFINITY,
    KNOCKBACK,
    LOOTING,
    LUCK_OF_THE_SEA,
    LURE,
    MENDING,
    POWER,
    PROJECTILE_PROTECTION,
    PROTECTION,
    PUNCH,
    RESPIRATION,
    SHARPNESS,
    SILK_TOUCH,
    SMITE,
    THORNS,
    UNBREAKING;

    public Enchantment asEnchantment() {
      return new Enchantment().withEffect(this);
    }

    public Enchantment atLevel(int level) {
      return asEnchantment().withLevel(level);
    }
  }
}
