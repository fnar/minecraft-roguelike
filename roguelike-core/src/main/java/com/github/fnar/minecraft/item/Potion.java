package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import com.github.fnar.util.Color;

import java.util.List;
import java.util.Random;

public class Potion extends RldBaseItem {

  private final List<com.github.fnar.minecraft.Effect> effects = Lists.newArrayList();
  // TODO: Move amplification and extension onto effect instead of potion
  // TODO: Potions can hold multiple effects
  private Effect effect;
  private Form form;
  private boolean isAmplified;
  private boolean isExtended;
  private Color color;

  public Potion() {
    this(Effect.AWKWARD);
  }

  public Potion(Effect effect) {
    this(effect, Form.REGULAR);
  }

  public Potion(Effect effect, Form form) {
    this(effect, form, Lists.newArrayList(), false, false);
  }

  public Potion(Effect effect, Form form, List<com.github.fnar.minecraft.Effect> effects, boolean isAmplified, boolean isExtended) {
    this.effect = effect;
    this.form = form;
    this.effects.addAll(effects);
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

  public Effect getEffect() {
    return effect;
  }

  public Potion withEffect(Effect effect) {
    this.effect = effect;
    return this;
  }

  public Form getForm() {
    return form;
  }

  public Potion withForm(Form Form) {
    this.form = Form;
    return this;
  }

  public List<com.github.fnar.minecraft.Effect> getEffects() {
    return effects;
  }

  public Potion withEffect(com.github.fnar.minecraft.Effect effects) {
    this.effects.add(effects);
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

  public Potion withColor(Color color) {
    this.color = color;
    return this;
  }

  public Color getColor() {
    return color;
  }

  public enum Amplification {

    UNAMPLIFIED(0),
    LEVEL_ONE(1),
    LEVEL_TWO(2);

    private final int level;

    Amplification(int level) {
      this.level = level;
    }

    public static Amplification chooseRandom(Random random) {
      return Amplification.values()[random.nextInt(Amplification.values().length)];
    }

    public int getLevel() {
      return level;
    }
  }

  public enum Effect {

    AWKWARD,
    EMPTY,
    FIRE_RESISTANCE,
    HARMING,
    HEALING,
    INVISIBILITY,
    LEAPING,
    LEVITATION,
    LUCK,
    MUNDANE,
    NIGHT_VISION,
    POISON,
    REGENERATION,
    SLOWNESS,
    SLOW_FALLING,
    STRENGTH,
    SWIFTNESS,
    THICK,
    TURTLE_MASTER,
    WATER,
    WATER_BREATHING,
    WEAKNESS,
    ;

    public static final Effect[] BUFF = {HEALING, LEAPING, REGENERATION, STRENGTH, SWIFTNESS};
    public static final Effect[] HARMFUL = {HARMING, POISON, SLOWNESS, WEAKNESS};
    public static final Effect[] QUIRK = {FIRE_RESISTANCE, INVISIBILITY, LEVITATION, NIGHT_VISION, SLOW_FALLING, WATER_BREATHING};

    public static Effect chooseRandom(Random random) {
      return chooseRandomAmong(random, Effect.values());
    }

    public static Effect chooseRandomAmong(Random random, Effect[] effects) {
      return effects[random.nextInt(effects.length)];
    }

    public Potion asItem() {
      return newPotion().withEffect(this);
    }
  }

  public enum Form {

    REGULAR,
    SPLASH,
    LINGERING;

    public static Form chooseRandom(Random rand) {
      return Form.values()[rand.nextInt(Form.values().length)];
    }
  }
}
