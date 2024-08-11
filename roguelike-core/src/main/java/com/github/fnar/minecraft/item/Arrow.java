package com.github.fnar.minecraft.item;

import java.util.Optional;
import java.util.Random;

public class Arrow extends RldBaseItem {

  private Potion tip;

  public static Arrow newArrow() {
    return new Arrow();
  }

  public static Arrow newRandomHarmful(Random random) {
    return newArrow().withTip(Potion.newPotion().withEffect(Potion.Effect.chooseRandomAmong(random, Potion.Effect.HARMFUL)));
  }

  @Override
  public ItemType getItemType() {
    return ItemType.ARROW;
  }

  public Arrow withTip(Potion potion) {
    this.tip = potion;
    return this;
  }

  public Optional<Potion> getTip() {
    return Optional.ofNullable(tip);
  }

}
