package com.github.srwaggon.minecraft.item.potion;

public class Effect {

  private EffectType effectType;
  private int amplification;
  private int duration;

  public Effect() {
    this(EffectType.SPEED, 0, 0);
  }

  public Effect(EffectType effectType, int amplification, int duration) {
    this.effectType = effectType;
    this.amplification = amplification;
    this.duration = duration;
  }

  public static Effect newEffect() {
    return new Effect();
  }

  public static Effect newEffect(EffectType effectType, int amplification, int duration) {
    return new Effect(effectType, amplification, duration);
  }

  public EffectType getType() {
    return effectType;
  }

  public Effect withType(EffectType effectType) {
    this.effectType = effectType;
    return this;
  }

  public int getDuration() {
    return duration;
  }

  public Effect withDuration(int duration) {
    this.duration = duration;
    return this;
  }

  public int getAmplification() {
    return amplification;
  }

  public Effect withAmplification(int amplification) {
    this.amplification = amplification;
    return this;
  }

  public Effect withAmplification(Amplification amplification) {
    this.amplification = amplification.getLevel();
    return this;
  }
}
