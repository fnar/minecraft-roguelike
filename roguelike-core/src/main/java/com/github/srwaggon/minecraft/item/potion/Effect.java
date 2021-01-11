package com.github.srwaggon.minecraft.item.potion;

public class Effect {

  private EffectType effectType;
  private int duration;
  private int amplification;

  public Effect(EffectType effectType, int duration, int amplification) {
    this.effectType = effectType;
    this.duration = duration;
    this.amplification = amplification;
  }

  public EffectType getEffectType() {
    return effectType;
  }

  public Effect withEffectType(EffectType effectType) {
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
