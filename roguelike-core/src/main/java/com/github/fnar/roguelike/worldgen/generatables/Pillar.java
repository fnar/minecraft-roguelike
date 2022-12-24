package com.github.fnar.roguelike.worldgen.generatables;

import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class Pillar extends BaseGeneratable {

  private int height = 3;
  private boolean withSupports = true;
  private BlockBrush pillarBrush = ColoredBlock.wool().red();
  private StairsBlock stairBrush = StairsBlock.netherBrick();

  private Pillar(WorldEditor worldEditor) {
    super(worldEditor);
  }

  @Override
  public Pillar generate(Coord at) {
    Coord top = at.copy().up(height).down();

    worldEditor.fillDown(top, this.pillarBrush);

    if (withSupports) {
      for (Direction cardinal : Direction.CARDINAL) {
        Coord support = top.copy().translate(cardinal);
        stairBrush.setUpsideDown(true).setFacing(cardinal).stroke(worldEditor, support, true, false);
      }
    }

    return this;
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

  @Override
  public Pillar withLevelSettings(LevelSettings levelSettings) {
    super.withLevelSettings(levelSettings);
    withPillarBrush(levelSettings.getTheme().getPrimary().getPillar());
    withPillarBrush(levelSettings.getTheme().getPrimary().getStair());
    return this;
  }
}
