package com.github.fnar.minecraft.worldgen.generatables;

import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Pillar extends BaseGeneratable {

  private int height = 3;
  private boolean withSupports = true;
  private BlockBrush pillarBrush;
  private StairsBlock stairBrush;

  private Pillar(WorldEditor worldEditor) {
    super(worldEditor);
  }

  @Override
  public BaseGeneratable generate(Coord at) {
    Coord top = at.copy().up(height).down();

    worldEditor.fillDown(top, this.pillarBrush);

    if (withSupports) {
      for (Direction cardinal: Direction.CARDINAL) {
        Coord support = top.copy().translate(cardinal);
        stairBrush.setUpsideDown(true).setFacing(cardinal).stroke(worldEditor, support, true, false);
      }
    }

    return this;
  }

  @Override
  public void generate(WorldEditor worldEditor, LevelSettings levelSettings, Coord origin, Direction facing) {
    Pillar.newPillar(worldEditor)
        .withPillarBrush(levelSettings.getTheme().getPrimary().getPillar())
        .withStairBrush(levelSettings.getTheme().getSecondary().getStair())
        .withLevelSettings(levelSettings)
        .generate(origin);
  }

  public static Pillar newPillar(WorldEditor worldEditor) {
    return new Pillar(worldEditor);
  }

  public Pillar withHeight(int height) {
    this.height = Math.max(2, height);
    return this;
  }

  public Pillar withSupports(boolean withSupports) {
    this.withSupports = withSupports;
    return this;
  }

  public Pillar withPillarBrush(BlockBrush pillarBrush) {
    this.pillarBrush = pillarBrush;
    return this;
  }

  public Pillar withStairBrush(StairsBlock stairBrush) {
    this.stairBrush = stairBrush;
    return this;
  }
}
