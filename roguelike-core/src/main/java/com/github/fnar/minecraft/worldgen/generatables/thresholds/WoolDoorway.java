package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.worldgen.generatables.BaseGeneratable;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WoolDoorway extends BaseGeneratable {

  public WoolDoorway(WorldEditor worldEditor) {
    super(worldEditor);
  }

  @Override
  public WoolDoorway generate(Coord at) {
    RectSolid rect = RectSolid.newRect(
        at.copy().translate(facing.left()),
        at.copy().translate(facing.right()).up(2)
    );
    getBlockThing(facing).fill(worldEditor, rect);
    return this;
  }

  private SingleBlockBrush getBlockThing(Direction facing) {
    switch (facing) {

      case NORTH:
        return ColoredBlock.wool().setColor(DyeColor.RED);
      case EAST:
        return ColoredBlock.wool().setColor(DyeColor.BLUE);
      case SOUTH:
        return ColoredBlock.wool().setColor(DyeColor.GREEN);
      case WEST:
        return ColoredBlock.wool().setColor(DyeColor.YELLOW);
      case UP:
        return ColoredBlock.wool().setColor(DyeColor.PURPLE);
      case DOWN:
        return ColoredBlock.wool().setColor(DyeColor.BLACK);
    }
    return null;
  }
}
