package com.github.fnar.minecraft.worldgen.generatables;

import com.google.common.collect.Maps;

import com.github.fnar.minecraft.worldgen.BlockPattern;

import java.util.Map;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Doorway implements Generatable {

  private final WorldEditor worldEditor;
  private final ThemeBase themeBase;
  private final String pattern;

  public Doorway(WorldEditor worldEditor, ThemeBase themeBase) {
    this.worldEditor = worldEditor;
    this.themeBase = themeBase;
    this.pattern = "" +
        "# B # \n" +
        "\n" +
        "# T # \n" +
        "\n" +
        "# # # \n";
  }

  public void generate(Coord origin, Direction facing) {
    Map<Character, BlockBrush> blockBrushMap = Maps.newHashMap();
    blockBrushMap.put('#', themeBase.getPrimary().getWall());
    blockBrushMap.put('B', themeBase.getPrimary().getDoor().setFacing(facing));
    blockBrushMap.put('T', themeBase.getPrimary().getDoor().setTop().setFacing(facing));

    new BlockPattern(worldEditor, pattern, blockBrushMap)
        .stroke(origin.copy().translate(facing.left()), facing, true, true);
  }
}
