package com.github.srwaggon.minecraft.block.decorative;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.Collections;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class VineBlock extends SingleBlockBrush {

  public VineBlock() {
    super(BlockType.VINE);
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    if (!editor.isAirBlock(origin)) {
      return false;
    }
    ArrayList<Direction> directions = new ArrayList<>(Direction.CARDINAL);
    directions.add(Direction.UP);
    Collections.shuffle(directions, editor.getRandom());
    for (Direction dir : directions) {
      if (dir == Direction.DOWN) {
        return false;
      }
      Coord c = origin.copy();
      if (editor.canPlace(this, c, dir)) {
        setFacing(dir);
        super.stroke(editor, origin, fillAir, replaceSolid);
        return true;
      }
    }
    return false;
  }

  public static VineBlock vine() {
    return new VineBlock();
  }
}
