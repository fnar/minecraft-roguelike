package com.github.srwaggon.minecraft.block.decorative;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class TallPlantBlock extends SingleBlockBrush {

  private TallPlant tallPlant = TallPlant.LARGE_FERN;
  private boolean isTop;

  public TallPlantBlock() {
    super(BlockType.TALL_PLANT);
  }

  public TallPlantBlock setTallPlant(TallPlant tallPlant) {
    this.tallPlant = tallPlant;
    return this;
  }

  public TallPlant getTallPlant() {
    return tallPlant;
  }

  public boolean isTop() {
    return isTop;
  }

  public TallPlantBlock setTop(boolean top) {
    isTop = top;
    return this;
  }

  public TallPlantBlock getTop() {
    return tallPlantBlock()
        .setTallPlant(getTallPlant())
        .setTop(true);
  }

  public TallPlantBlock getBottom() {
    return tallPlantBlock()
        .setTallPlant(getTallPlant())
        .setTop(false);
  }

  public static TallPlantBlock tallPlantBlock() {
    return new TallPlantBlock();
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    boolean isSuccess = super.stroke(editor, pos, fillAir, replaceSolid);
    if (!isTop()) {
      isSuccess = isSuccess && getTop().stroke(editor, pos.copy().up(1), fillAir, replaceSolid);
    }
    return isSuccess;
  }

}
