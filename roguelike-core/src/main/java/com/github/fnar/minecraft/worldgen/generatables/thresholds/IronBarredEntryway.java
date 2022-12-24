package com.github.fnar.minecraft.worldgen.generatables.thresholds;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.worldgen.BlockPattern;
import com.github.fnar.minecraft.worldgen.generatables.Generatable;

import java.util.Map;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class IronBarredEntryway implements Generatable {

  public void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('W', levelSettings.getTheme().getPrimary().getWall());
    blockBrushMap.put('#', BlockType.IRON_BAR.getBrush());

    String pattern = "" +
        "W # # # W \n\n" +
        "W # # # W \n\n" +
        "W # # # W \n";

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(origin.copy().translate(facing.left(), 2), facing);
  }
}
