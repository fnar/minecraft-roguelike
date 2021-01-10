package com.github.srwaggon.roguelike.minecraft.item;

import com.google.common.collect.Lists;

import java.util.List;

import greymerk.roguelike.treasure.loot.PotionEffect;
import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.PotionType;

public class Potion implements Item {

  private PotionType potionType;
  private PotionForm potionForm;
  private List<PotionEffect> potionEffects;
  private int amplification;
  private int duration;

  public Potion(PotionType potionType) {
    this(potionType, PotionForm.REGULAR);
  }

  public Potion(PotionType potionType, PotionForm potionForm) {
    this(potionType, potionForm, Lists.newArrayList(), 0, 30);
  }

  public Potion(PotionType potionType, PotionForm potionForm, List<PotionEffect> potionEffects, int amplification, int duration) {
    this.potionType = potionType;
    this.potionForm = potionForm;
    this.potionEffects = potionEffects;
    this.amplification = amplification;
    this.duration = duration;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.POTION;
  }

  public PotionType getPotionType() {
    return potionType;
  }

  public void setPotionType(PotionType potionType) {
    this.potionType = potionType;
  }

  public PotionForm getPotionForm() {
    return potionForm;
  }

  public void setPotionForm(PotionForm potionForm) {
    this.potionForm = potionForm;
  }

  public List<PotionEffect> getPotionEffects() {
    return potionEffects;
  }

  public void setPotionEffects(List<PotionEffect> potionEffects) {
    this.potionEffects = potionEffects;
  }

  public int getAmplification() {
    return amplification;
  }

  public void setAmplification(int amplification) {
    this.amplification = amplification;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }
}
