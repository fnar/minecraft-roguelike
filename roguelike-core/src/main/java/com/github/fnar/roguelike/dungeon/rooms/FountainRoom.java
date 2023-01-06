package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.roguelike.worldgen.generatables.Fountain;
import com.github.fnar.roguelike.worldgen.generatables.Generatable;
import com.github.fnar.roguelike.worldgen.generatables.Pillar;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class FountainRoom extends BaseRoom {

  public FountainRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.size = 6;
    this.ceilingHeight = 5;
  }

  @Override
  protected void generateDecorations(Coord at, List<Direction> entrances) {
    Fountain.newFountain(worldEditor).generate(at);

    Generatable pillar = Pillar.newPillar(worldEditor).withHeight(getCeilingHeight()).withTheme(theme());
    for (Direction cardinal : Direction.cardinals()) {
      int pillarDist = getWallDist() - 1;
      pillar.generate(at.copy().translate(cardinal, pillarDist).translate(cardinal.left(), pillarDist));
    }
  }

}
