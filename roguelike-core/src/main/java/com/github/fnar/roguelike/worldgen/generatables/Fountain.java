package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class Fountain extends BaseGeneratable {

  private BlockBrush liquid = BlockType.WATER_FLOWING.getBrush();

  protected Fountain(WorldEditor worldEditor) {
    super(worldEditor);
  }

  public static Fountain newFountain(WorldEditor worldEditor) {
    return new Fountain(worldEditor);
  }

  @Override
  public BaseGeneratable generate(Coord at) {
    SingleBlockBrush stoneBrick = BlockType.STONE_BRICK.getBrush();
    stoneBrick.fill(worldEditor, at.newRect(2).down());
    liquid.fill(worldEditor, at.newRect(2));
    stoneBrick.fill(worldEditor, at.newRect(1).withHeight(3));
    liquid.stroke(worldEditor, at.copy().up(2));

    for (Direction cardinal : Direction.cardinals()) {
      Coord center = at.copy().translate(cardinal, 2);
      StairsBlock.stoneBrick().setUpsideDown(false).setFacing(cardinal).fill(worldEditor, RectSolid.newRect(
          center.copy().translate(cardinal.left()),
          center.copy().translate(cardinal.right())
      ));
    }
    return this;
  }

  public Fountain withLiquid(BlockBrush liquidBlockBrush) {
    this.liquid = liquidBlockBrush;
    return this;
  }

}
