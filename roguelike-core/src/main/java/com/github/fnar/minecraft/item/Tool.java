package com.github.fnar.minecraft.item;

import java.util.Objects;

import greymerk.roguelike.treasure.loot.Quality;
import lombok.ToString;

@ToString(callSuper = true)
public class Tool extends RldBaseItem {

  private final ToolType toolType;
  private Quality quality = Quality.WOOD;

  public Tool(ToolType toolType) {
    this.toolType = toolType;
  }

  public ToolType getToolType() {
    return toolType;
  }

  public Quality getQuality() {
    return quality;
  }

  public Tool withQuality(Quality quality) {
    this.quality = quality;
    return this;
  }

  public Tool wooden() {
    withQuality(Quality.WOOD);
    return this;
  }

  public Tool stone() {
    withQuality(Quality.STONE);
    return this;
  }

  public Tool iron() {
    withQuality(Quality.IRON);
    return this;
  }

  public Tool golden() {
    withQuality(Quality.GOLD);
    return this;
  }

  public Tool diamond() {
    withQuality(Quality.DIAMOND);
    return this;
  }

  @Override
  public ItemType getItemType() {
    return ItemType.TOOL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tool tool = (Tool) o;
    return toolType == tool.toolType && quality == tool.quality;
  }

  @Override
  public int hashCode() {
    return Objects.hash(toolType, quality);
  }
}
