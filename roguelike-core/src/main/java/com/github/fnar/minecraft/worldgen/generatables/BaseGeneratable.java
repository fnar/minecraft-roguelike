package com.github.fnar.minecraft.worldgen.generatables;

import java.util.List;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class BaseGeneratable implements Generatable {

  protected WorldEditor worldEditor;
  protected LevelSettings levelSettings;
  protected Direction facing = Direction.UP;

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

}
