package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.worldgen.generatables.NetherPortal;

import java.util.List;
import java.util.stream.Stream;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class NetherPortalRoom extends BaseRoom {

  public NetherPortalRoom(RoomSetting roomsSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomsSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Direction front = getEntrance(entrances);

    generateWalls(origin, front);
    theFloorsAreFloors(origin, front);
    generateCatwalks(origin);
    theFloorIsLava(origin, front);
    createPathFromEachEntranceToTheCenterOverTheLiquid(origin, entrances);
    ceilingChan(origin, front);
    generateNetherPortalWithPlatform(origin, front);
    generateDoorways(origin, entrances, getSize());

    return null;
  }

  private void generateWalls(Coord origin, Direction front) {
    RectHollow.newRect(
        origin.copy().translate(front.left(), getSize()).translate(front, getSize()).copy().down(3),
        origin.copy().translate(front.right(), getSize()).translate(front.back(), getSize()).copy().up(getHeight())
    ).fill(worldEditor, walls());
  }

  private void theFloorsAreFloors(Coord origin, Direction front) {
    RectSolid.newRect(
        origin.copy().translate(front.left(), 3).translate(front, 3).down(),
        origin.copy().translate(front.right(), 3).translate(front.back(), 3).down(2)
    ).fill(worldEditor, floors());
  }

  private void generateCatwalks(Coord origin) {
    StairsBlock stair = stairs();

    for (Direction side : Direction.cardinals()) {
      Coord catwalkOrigin = origin.copy().translate(side, getSize() - 1);
      RectSolid.newRect(
          catwalkOrigin.copy().translate(side.left(), getSize()),
          catwalkOrigin.copy().translate(side.right(), getSize()).translate(side.back()).down(2)
      ).fill(worldEditor, floors());

      RectSolid.newRect(
          catwalkOrigin.copy().translate(side.left(), 2),
          catwalkOrigin.copy().translate(side.right(), 2).translate(side.back())
      ).fill(worldEditor, SingleBlockBrush.AIR);

      for (Direction orthogonal : side.orthogonals()) {
        Coord place = catwalkOrigin.copy().translate(orthogonal, 3);
        stair.setUpsideDown(false).setFacing(orthogonal.reverse());
        stair.stroke(worldEditor, place);
        stair.stroke(worldEditor, place.translate(side.back()));
      }
    }
  }

  private void createPathFromEachEntranceToTheCenterOverTheLiquid(Coord origin, List<Direction> entrances) {
    Direction.cardinals().stream()
        .filter(direction -> !entrances.contains(direction))
        .forEach(direction -> RectSolid.newRect(
            origin.copy().translate(direction.left()).down(),
            origin.copy().translate(direction.right()).translate(direction, getSize()).down(2)
        ).fill(worldEditor, floors()));
  }

  private void ceilingChan(Coord origin, Direction front) {
    // ceiling-chan >///<
    RectSolid.newRect(
        origin.copy().translate(front.left(), getSize()).translate(front, getSize()).up(getHeight()),
        origin.copy().translate(front.right(), getSize()).translate(front.back(), getSize()).up(getHeight())
    ).fill(worldEditor, floors());
  }

  private void generateNetherPortalWithPlatform(Coord origin, Direction front) {
    BlockBrush pillar = pillars();
    RectSolid.newRect(
        origin.copy().translate(front).translate(front.left(), 3),
        origin.copy().translate(front.back()).translate(front.right(), 3).up(getHeight() - 1)
    ).fill(worldEditor, pillar);

    // portal platform
    RectSolid.newRect(
        origin.copy().translate(front.left(), 2).translate(front, 2),
        origin.copy().translate(front.right(), 2).translate(front.back(), 2)
    ).fill(worldEditor, pillar);

    StairsBlock stairsBrush = stairs();
    Stream.of(front, front.reverse())
        .forEach(side -> {
          Coord platformStairs = origin.copy().translate(side, 2);
          stairsBrush.setUpsideDown(false);
          stairsBrush.setFacing(side);
          stairsBrush.stroke(worldEditor, platformStairs);
          stairsBrush.stroke(worldEditor, platformStairs.copy().translate(front.left()));
          stairsBrush.stroke(worldEditor, platformStairs.copy().translate(front.right()));
        });

    // nether portal, atop the portal platform
    Coord portalBase = origin.copy();
    new NetherPortal(worldEditor).generate(portalBase, front, 5, 7);

    // 2x cheeky spawners beneath of portal
    for (Direction orthogonal : front.orthogonals()) {
      generateSpawner(portalBase.copy().down().translate(orthogonal));
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
