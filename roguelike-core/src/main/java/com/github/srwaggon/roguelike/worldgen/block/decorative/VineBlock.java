package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import java.util.ArrayList;
import java.util.Collections;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
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
    ArrayList<Cardinal> directions = new ArrayList<>(Cardinal.DIRECTIONS);
    directions.add(Cardinal.UP);
    Collections.shuffle(directions, editor.getRandom());
    for (Cardinal dir : directions) {
      if (dir == Cardinal.DOWN) {
        return false;
      }
      Coord c = new Coord(origin);
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
