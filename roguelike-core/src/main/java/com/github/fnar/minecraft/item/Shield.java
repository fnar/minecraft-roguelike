package com.github.fnar.minecraft.item;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class Shield extends RldBaseItem {

  private final List<Design> designs = Lists.newArrayList();

  @Override
  public ItemType getItemType() {
    return ItemType.SHIELD;
  }

  public void addRandomPattern(Random random) {
    withDesign(Design.randomDesign(random));
  }

  public Shield withDesign(Design design) {
    designs.add(design);
    return this;
  }

  public List<Design> getDesigns() {
    return designs;
  }
}
