package com.github.srwaggon.minecraft.item;

import java.util.Optional;
import java.util.Random;

public class Arrow implements RldItem {

  private Potion tip;

  public static Arrow newArrow() {
    return new Arrow();
  }

  public static Arrow newRandomHarmful(Random random) {
    return newArrow().withTip(Potion.newPotion().withType(Potion.Type.chooseRandomAmong(random, Potion.Type.HARMFUL)));
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
