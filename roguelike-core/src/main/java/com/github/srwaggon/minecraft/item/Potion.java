package com.github.srwaggon.minecraft.item;

import com.google.common.collect.Lists;

import java.util.List;

import greymerk.roguelike.treasure.loot.PotionEffect;
import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.PotionType;

public class Potion implements RldItem {

  private PotionType type;
  private PotionForm form;
  private List<PotionEffect> effects;
  private boolean isAmplified;
  private boolean isExtended;

  public Potion() {
    this(PotionType.HEALING);
  }

  public Potion(PotionType type) {
    this(type, PotionForm.REGULAR);
  }

  public Potion(PotionType type, PotionForm form) {
    this(type, form, Lists.newArrayList(), false, false);
  }

  public Potion(PotionType type, PotionForm form, List<PotionEffect> effects, boolean isAmplified, boolean isExtended) {
    this.type = type;
    this.form = form;
    this.effects = effects;
    this.isAmplified = isAmplified;
    this.isExtended = isExtended;
  }

  public static Potion newPotion() {
    return new Potion();
  }

  @Override
  public ItemType getItemType() {
    return ItemType.POTION;
  }

  public PotionType getType() {
    return type;
  }

  public Potion withType(PotionType type) {
    this.type = type;
    return this;
  }

  public PotionForm getForm() {
    return form;
  }

  public Potion withForm(PotionForm Form) {
    this.form = Form;
    return this;
  }

  public List<PotionEffect> getEffects() {
    return effects;
  }

  public Potion withEffects(List<PotionEffect> Effects) {
    this.effects = Effects;
    return this;
  }

  public boolean isAmplified() {
    return isAmplified;
  }

  public Potion withAmplification() {
    return withAmplification(true);
  }

  public Potion withAmplification(boolean isAmplified) {
    this.isAmplified = isAmplified;
    return this;
  }

  public boolean isExtended() {
    return isExtended;
  }

  public Potion withExtension() {
    return withExtension(true);
  }

  public Potion withExtension(boolean isExtended) {
    this.isExtended = isExtended;
    return this;
  }

}
