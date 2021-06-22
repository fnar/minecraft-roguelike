package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.Effect;

import java.util.List;
import java.util.Random;

public class Potion implements RldItem {

  private Type type;
  private Form form;
  private final List<Effect> effects = Lists.newArrayList();
  private boolean isAmplified;
  private boolean isExtended;

  public Potion() {
    this(Type.AWKWARD);
  }

  public Potion(Type type) {
    this(type, Form.REGULAR);
  }

  public Potion(Type type, Form form) {
    this(type, form, Lists.newArrayList(), false, false);
  }

  public Potion(Type type, Form form, List<Effect> effects, boolean isAmplified, boolean isExtended) {
    this.type = type;
    this.form = form;
    this.effects.addAll(effects);
    this.isAmplified = isAmplified;
    this.isExtended = isExtended;
  }

  public static Potion newPotion() {
    return new Potion();
  }

  public static Potion newStrongPoison() {
    return newPotion().withType(Type.POISON).withAmplification();
  }

  @Override
  public ItemType getItemType() {
    return ItemType.POTION;
  }

  public Type getType() {
    return type;
  }

  public Potion withType(Type type) {
    this.type = type;
    return this;
  }

  public Form getForm() {
    return form;
  }

  public Potion withForm(Form Form) {
    this.form = Form;
    return this;
  }

  public List<Effect> getEffects() {
    return effects;
  }

  public Potion withEffect(Effect effects) {
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

  public enum Amplification {

    UNAMPLIFIED(0),
    LEVEL_ONE(1),
    LEVEL_TWO(2);

    private final int level;

    Amplification(int level) {
      this.level = level;
    }

    public int getLevel() {
      return level;
    }

    public static Amplification chooseRandom(Random random) {
      return Amplification.values()[random.nextInt(Amplification.values().length)];
    }
  }

  public enum Type {

    AWKWARD,
    FIRE_RESISTANCE,
    HARMING,
    HEALING,
    INVISIBILITY,
    LEAPING,
    LEVITATION,
    LUCK,
    NIGHT_VISION,
    POISON,
    REGENERATION,
    SLOWNESS,
    SLOW_FALLING,
    STRENGTH,
    SWIFTNESS,
    TURTLE_MASTER,
    WATER_BREATHING,
    WEAKNESS,
    ;

    public static final Type[] BUFF = {HEALING, LEAPING, REGENERATION, STRENGTH, SWIFTNESS};
    public static final Type[] HARMFUL = {HARMING, POISON, SLOWNESS, WEAKNESS};
    public static final Type[] QUIRK = {FIRE_RESISTANCE, INVISIBILITY, LEVITATION, NIGHT_VISION, SLOW_FALLING, WATER_BREATHING};

    public static Type chooseRandom(Random random) {
      return chooseRandomAmong(random, Type.values());
    }

    public static Type chooseRandomAmong(Random random, Type[] types) {
      return types[random.nextInt(types.length)];
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
