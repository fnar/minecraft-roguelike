package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Shield extends RldBaseItem {

  private final List<Design> designs = Lists.newArrayList();

  private Shield() {
  }

  public static Shield newShield() {
    return new Shield();
  }

  @Override
  public ItemType getItemType() {
    return ItemType.SHIELD;
  }

  public Shield withRandomPattern(Random random) {
    withDesign(Design.randomDesign(random));
    return this;
  }

  public Shield withRandomPatterns(Random random, int count) {
    IntStream.range(0, count).forEach(i -> this.withRandomPattern(random));
    return this;
  }

  public Shield withDesign(Design design) {
    designs.add(design);
    return this;
  }

  public List<Design> getDesigns() {
    return designs;
  }
}
