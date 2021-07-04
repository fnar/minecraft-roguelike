package com.github.fnar.minecraft.worldgen.generatables;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.worldgen.BlockPattern;

import java.util.Map;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorway implements Generatable {

  public void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('#', levelSettings.getTheme().getPrimary().getWall());
    blockBrushMap.put('B', levelSettings.getTheme().getPrimary().getDoor().setFacing(facing));
    blockBrushMap.put('T', levelSettings.getTheme().getPrimary().getDoor().setTop().setFacing(facing));

    String pattern = "" +
        "# # B # # \n\n" +
        "# # T # # \n\n" +
        "# # # # # \n";

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(origin.copy().translate(facing.left(), 2), facing);
  }
}
