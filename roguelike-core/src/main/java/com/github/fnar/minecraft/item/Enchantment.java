package com.github.fnar.minecraft.item;

public class Enchantment {

  private Effect effect;
  private int level = 1;

  public Effect getEffect() {
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
    BINDING_CURSE,
    BLAST_PROTECTION,
    CHANNELING,
    DEPTH_STRIDER,
    EFFICIENCY,
    FEATHER_FALLING,
    FIRE_ASPECT,
    FIRE_PROTECTION,
    FLAME,
    FORTUNE,
    FROST_WALKER,
    IMPALING,
    INFINITY,
    KNOCKBACK,
    LOOTING,
    LOYALTY,
    LUCK_OF_THE_SEA,
    LURE,
    MENDING,
    MULTISHOT,
    PIERCING,
    POWER,
    PROJECTILE_PROTECTION,
    PROTECTION,
    PUNCH,
    QUICK_CHARGE,
    RESPIRATION,
    RIPTIDE,
    SHARPNESS,
    SILK_TOUCH,
    SMITE,
    SWEEPING,
    THORNS,
    UNBREAKING,
    VANISHING_CURSE;

    public Enchantment asEnchantment() {
      return new Enchantment().withEffect(this);
    }

    public Enchantment atLevel(int level) {
      return asEnchantment().withLevel(level);
    }
  }
}
