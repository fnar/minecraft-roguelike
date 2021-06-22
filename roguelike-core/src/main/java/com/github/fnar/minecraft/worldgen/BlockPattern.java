package com.github.fnar.minecraft.worldgen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class BlockPattern {

  private final WorldEditor worldEditor;
  private final String pattern;
  private final Map<Character, BlockBrush> blockBrushMap;
  private String sliceDelimiter = "\n\n";
  private String rowDelimiter = "\n";
  private String blockDelimiter = " ";

  public BlockPattern(WorldEditor worldEditor, String pattern, Map<Character, BlockBrush> blockBrushMap) {
    this.worldEditor = worldEditor;
    this.pattern = pattern;
    this.blockBrushMap = blockBrushMap;
  }

  public void paintPattern(Coord origin, Direction facing) {
    List<List<List<BlockBrush>>> patternOfBrushes = parseSlices(pattern);

    Coord cursor = origin.copy();
    for (List<List<BlockBrush>> slice : patternOfBrushes) {
      Coord sliceCursor = cursor.copy();
      for (List<BlockBrush> row : slice) {
        Coord rowCursor = sliceCursor.copy();
        for (BlockBrush brush : row) {
          brush.stroke(worldEditor, rowCursor);
          rowCursor.translate(facing.right());
        }
        sliceCursor.translate(facing.back());
      }
      cursor.up();
    }
  }

  private List<List<List<BlockBrush>>> parseSlices(String pattern) {
    String[] slices = pattern.split(sliceDelimiter);
    return Arrays.stream(slices)
        .map(this::parseSlice)
        .collect(Collectors.toList());
  }

  private List<List<BlockBrush>> parseSlice(String slicePattern) {
    String[] rows = slicePattern.split(rowDelimiter);
    return Arrays.stream(rows)
        .map(this::parseRow)
        .collect(Collectors.toList());
  }

  private List<BlockBrush> parseRow(String row) {
    if (row.isEmpty()) {
      return Collections.emptyList();
    }
    return Arrays.stream(row.split(blockDelimiter))
        .map(str -> str.charAt(0))
        .map(blockBrushMap::get)
        .collect(Collectors.toList());
  }

  public void setSliceDelimiter(String sliceDelimiter) {
    this.sliceDelimiter = sliceDelimiter;
  }

  public void setRowDelimiter(String rowDelimiter) {
    this.rowDelimiter = rowDelimiter;
  }

  public void setBlockDelimiter(String blockDelimiter) {
    this.blockDelimiter = blockDelimiter;
  }
}
