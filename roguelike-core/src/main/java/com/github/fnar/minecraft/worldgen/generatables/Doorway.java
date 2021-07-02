package com.github.fnar.minecraft.worldgen.generatables;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.worldgen.BlockPattern;

import java.util.Map;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorway implements Generatable {

  private final WorldEditor worldEditor;
  private final Theme theme;
  private final String pattern;

  public Doorway(WorldEditor worldEditor, Theme theme) {
    this.worldEditor = worldEditor;
    this.theme = theme;
    this.pattern = "" +
        "# B # \n" +
        "\n" +
        "# T # \n" +
        "\n" +
        "# # # \n";
  }

  public void generate(Coord origin, Direction facing) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('#', theme.getPrimary().getWall());
    blockBrushMap.put('B', theme.getPrimary().getDoor().setFacing(facing));
    blockBrushMap.put('T', theme.getPrimary().getDoor().setTop().setFacing(facing));

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(origin.copy().translate(facing.left()), facing, true, true);
  }
}
