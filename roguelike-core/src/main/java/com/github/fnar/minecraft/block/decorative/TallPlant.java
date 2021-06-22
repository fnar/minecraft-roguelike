package com.github.fnar.minecraft.block.decorative;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.redstone.TrapdoorBlock;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public enum TallPlant {

  SUNFLOWER,
  LILAC,
  DOUBLE_TALL_GRASS,
  LARGE_FERN,
  ROSE_BUSH,
  PEONY,
  ;

  public static void placePlant(WorldEditor editor, Coord origin, Direction dir) {
    placePlanter(editor, origin);
    chooseRandom(editor.getRandom(origin)).getBrush()
        .setFacing(dir)
        .stroke(editor, origin.copy().up());
  }

  public static void placePlanter(WorldEditor editor, Coord origin) {
    BlockType.DIRT_PODZOL.getBrush().stroke(editor, origin);

    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = origin.copy().translate(dir);
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
