package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.roguelike.worldgen.generatables.Generatable;
import com.github.fnar.roguelike.worldgen.generatables.Pillar;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class SmallLiquidPitRoom extends BaseRoom {

  public SmallLiquidPitRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 3 + random().nextInt(4);
    this.ceilingHeight = Math.max(4, wallDist);
  }

  @Override
  protected void generateDecorations(Coord at, List<Direction> entrances) {
    generateLiquid(at);
    generatePillars(at);
    generateSpawners(at);
  }

  private void generateLiquid(Coord at) {
    int liquidHeight = 1 + random().nextInt(3);
    int radius = Math.max(1, getWallDist() - (random().nextBoolean() ? 1 : 0) - random().nextInt(getWallDist() / 2));
    primaryLiquidBrush().fill(worldEditor, at.newRect(radius).withHeight(liquidHeight).down(liquidHeight));
  }

  private void generatePillars(Coord at) {
    Generatable pillar = Pillar.newPillar(worldEditor).withHeight(getCeilingHeight()).withTheme(theme());
    if (getWallDist() >= 2) {
      for (Direction cardinal : Direction.cardinals()) {
        int pillarDist = getWallDist() - 1;
        pillar.generate(at.copy().translate(cardinal, pillarDist).translate(cardinal.left(), pillarDist));
      }
    }

    if (getWallDist() >= 6 && random().nextBoolean()) {
      for (Direction cardinal : Direction.cardinals()) {
        pillar.generate(at.copy().translate(cardinal).translate(cardinal.left()));
      }
    }
  }

  private void generateSpawners(Coord at) {
    for (Direction cardinal : Direction.cardinals()) {
      if (random().nextDouble() < 0.125) {
        Coord spawnerCoord = at.copy().translate(cardinal, getWallDist()).translate(cardinal.left(), getWallDist());
        generateSpawner(spawnerCoord);
      }
    }
  }

}
