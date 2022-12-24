package com.github.fnar.roguelike.worldgen.generatables.thresholds;

import com.google.common.collect.Maps;

import com.github.fnar.roguelike.worldgen.BlockPattern;
import com.github.fnar.roguelike.worldgen.generatables.BaseGeneratable;

import java.util.Map;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorway extends BaseGeneratable {

  public Doorway(WorldEditor worldEditor) {
    super(worldEditor);
  }

  public Doorway generate(Coord at) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('#', levelSettings.getTheme().getPrimary().getWall());
    blockBrushMap.put('B', levelSettings.getTheme().getPrimary().getDoor().setFacing(facing));
    blockBrushMap.put('T', levelSettings.getTheme().getPrimary().getDoor().setTop().setFacing(facing));

    String pattern = "" +
        "# # B # # \n\n" +
        "# # T # # \n\n" +
        "# # # # # \n";

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(at.copy().translate(facing.left(), 2), facing);

    return this;
  }
}
