package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.redstone.TrapdoorBlock;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public enum TallPlant {

  SUNFLOWER,
  LILAC,
  DOUBLE_TALL_GRASS,
  LARGE_FERN,
  ROSE_BUSH,
  PEONY,
  ;

  public static void placePlant(WorldEditor editor, Coord origin, Cardinal dir) {
    placePlanter(editor, origin);
    chooseRandom(editor.getRandom()).getBrush()
        .setFacing(dir)
        .stroke(editor, new Coord(origin).up(1));
  }

  public static void placePlanter(WorldEditor editor, Coord origin) {
    BlockType.DIRT_PODZOL.getBrush().stroke(editor, origin);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Coord cursor = new Coord(origin).translate(dir);
      TrapdoorBlock.wood()
          .setFlushWithTop()
          .setOpen()
          .setFacing(dir.reverse())
          .stroke(editor, cursor, true, false);
    }
  }

  public TallPlantBlock getBrush() {
    return TallPlantBlock.tallPlantBlock().setTallPlant(this);
  }

  public static TallPlant chooseRandom(Random random) {
    return values()[random.nextInt(values().length)];
  }
}
