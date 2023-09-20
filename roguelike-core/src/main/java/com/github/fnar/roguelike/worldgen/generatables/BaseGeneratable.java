package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class BaseGeneratable implements Generatable {

  protected WorldEditor worldEditor;
  protected LevelSettings levelSettings;
  protected Direction facing = Direction.UP;

  protected StairsBlock stairs = StairsBlock.netherBrick();
  protected BlockBrush pillar = ColoredBlock.wool().red();
  protected BlockBrush walls = BlockType.STONE_BRICKS.getBrush();

  protected BaseGeneratable(WorldEditor worldEditor) {
    this.worldEditor = worldEditor;
  }

  public abstract BaseGeneratable generate(Coord at);

  public BaseGeneratable generate(List<Coord> coords) {
    coords.forEach(this::generate);
    return this;
  }

  public BaseGeneratable withLevelSettings(LevelSettings levelSettings) {
    this.levelSettings = levelSettings;
    return this;
  }

  public BaseGeneratable withFacing(Direction facing) {
    this.facing = facing;
    return this;
  }

  public BaseGeneratable withWalls(BlockBrush walls) {
    this.walls = walls;
    return this;
  }

  public BaseGeneratable withStairs(StairsBlock stairs) {
    this.stairs = stairs;
    return this;
  }

  public BaseGeneratable withTheme(Theme theme) {
    withWalls(theme.getPrimary().getWall());
    withStairs(theme.getPrimary().getStair());
    withPillar(theme.getPrimary().getPillar());
    return this;
  }

  public BaseGeneratable withPillar(BlockBrush pillar) {
    this.pillar = pillar;
    return this;
  }

}
