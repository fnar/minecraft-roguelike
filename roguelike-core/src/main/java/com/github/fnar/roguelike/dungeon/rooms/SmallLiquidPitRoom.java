package com.github.fnar.roguelike.dungeon.rooms;

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
    this.size = 3 + random().nextInt(4);
    this.height = Math.max(4, size);
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
    liquid().fill(worldEditor, at.newRect(radius).withHeight(liquidHeight).down(liquidHeight));
  }

  private void generatePillars(Coord at) {
    Pillar pillar = Pillar.newPillar(worldEditor).withTheme(theme()).withHeight(getCeilingHeight());
    if (size >= 2) {
      for (Direction cardinal : Direction.cardinals()) {
        int pillarDist = getWallDist() - 1;
        pillar.generate(at.copy().translate(cardinal, pillarDist).translate(cardinal.left(), pillarDist));
      }
    }

    if (size >= 6 && random().nextBoolean()) {
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
