package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.worldgen.BlockPattern;
import com.github.fnar.minecraft.worldgen.generatables.BaseGeneratable;

import java.util.Map;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class IronBarredEntryway extends BaseGeneratable {

  public IronBarredEntryway(WorldEditor worldEditor) {
    super(worldEditor);
  }

  public IronBarredEntryway generate(Coord at) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('W', levelSettings.getTheme().getPrimary().getWall());
    blockBrushMap.put('#', BlockType.IRON_BAR.getBrush());

    String pattern = "" +
        "W # # # W \n\n" +
        "W # # # W \n\n" +
        "W # # # W \n";

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(at.copy().translate(facing.left(), 2), facing);

    return this;
  }
}
