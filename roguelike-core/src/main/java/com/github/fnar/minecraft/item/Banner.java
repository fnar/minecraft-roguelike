package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.DyeColor;

public class Banner extends RldBaseItem {

  private final List<Design> designs = Lists.newArrayList();

  public Banner() {
  }

  @Override
  public ItemType getItemType() {
    return ItemType.BANNER;
  }

  public void addRandomPattern(Random random) {
    withDesign(DesignPattern.chooseRandom(random).withColor(DyeColor.chooseRandom(random)));
  }

  public Banner withDesign(Design design) {
    designs.add(design);
    return this;
  }

  public Banner withDesigns(List<Design> designs) {
    this.designs.addAll(designs);
    return this;
  }

  public List<Design> getDesigns() {
    return designs;
  }
}
