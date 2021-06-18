package com.github.fnar.roguelike.dungeon.rooms;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;
import java.util.stream.Stream;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class NetherPortalRoom extends DungeonBase {

  public NetherPortalRoom(RoomSetting roomsSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomsSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Coord realOrigin = origin.copy().down();

    generateWalls(realOrigin, entrances);

    generateNetherPortal(realOrigin, entrances);

    return null;
  }

  private void generateWalls(Coord origin, List<Direction> entrances) {
    Direction front = entrances.get(0);
    Direction left = front.left();
    Direction right = front.right();
    Direction back = front.back();

    Coord frontLeftCorner = origin.copy().translate(left, getSize()).translate(front, getSize());
    Coord backRightCorner = origin.copy().translate(right, getSize()).translate(back, getSize());
    Coord upperFrontLeftCorner = frontLeftCorner.copy().up(getHeight());
    Coord upperBackRightCorner = backRightCorner.copy().up(getHeight());

    BlockBrush walls = levelSettings.getTheme().getPrimary().getWall();
    RectHollow.newRect(
        frontLeftCorner.copy().down(2),
        upperBackRightCorner
    ).fill(worldEditor, walls, false, true);

    // the floor is lava
    BlockBrush liquid = levelSettings.getTheme().getPrimary().getLiquid();
    RectSolid.newRect(
        frontLeftCorner.copy().translate(back).translate(right).down(),
        backRightCorner.copy().translate(front).translate(left)
    ).fill(worldEditor, liquid, true, true);

    BlockBrush floors = levelSettings.getTheme().getPrimary().getFloor();

    RectSolid.newRect(
        origin.copy().translate(left, 3).translate(front, 3),
        origin.copy().translate(right, 3).translate(front.reverse(), 3)
    ).fill(worldEditor, floors);

    // create a path to each entrance over the liquid
    for (Direction entrance : entrances) {
      RectSolid.newRect(
          origin.copy().translate(entrance.left()),
          origin.copy().translate(entrance.right()).translate(entrance, getSize())
      ).fill(worldEditor, floors);
    }

    // ceiling-chan >///<
    RectSolid.newRect(
        upperFrontLeftCorner,
        upperBackRightCorner
    ).fill(worldEditor, floors);
  }

  private void generateNetherPortal(Coord origin, List<Direction> entrances) {
    Direction mainEntrance = entrances.get(0);
    Direction[] orthogonals = mainEntrance.orthogonals();

    // portal platform
    RectSolid.newRect(
        origin.copy().up().translate(orthogonals[0], 2).translate(orthogonals[0].clockwise(), 2),
        origin.copy().up().translate(orthogonals[1], 2).translate(orthogonals[1].clockwise(), 2)
    ).fill(worldEditor, levelSettings.getTheme().getPrimary().getWall());

    Stream.of(mainEntrance, mainEntrance.reverse()).forEach(side -> {
      StairsBlock stairsBrush = levelSettings.getTheme().getPrimary().getStair().setUpsideDown(false);
      if (entrances.contains(side)) {
        Coord platformStairs = origin.copy().up().translate(side, 2);
        stairsBrush.setFacing(side);
        stairsBrush.stroke(worldEditor, platformStairs);
        stairsBrush.stroke(worldEditor, platformStairs.copy().translate(orthogonals[0]));
        stairsBrush.stroke(worldEditor, platformStairs.copy().translate(orthogonals[1]));
      }
    });

    // nether portal, atop the portal platform
    Coord portalBase = origin.copy().up();
    RectHollow.newRect(
        portalBase.copy().translate(orthogonals[0], 2),
        portalBase.copy().translate(orthogonals[1], 2).up(5)
    ).fill(worldEditor, new SingleBlockBrush(BlockType.OBSIDIAN));

    // 2x cheeky spawners beneath of portal
    for (Direction orthogonal : orthogonals) {
      generateSpawner(origin.copy().translate(orthogonal));
    }
  }

  private int getHeight() {
    return 8;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
