package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.worldgen.generatables.NetherPortal;

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
    Direction front = entrances.get(0);

    generateWalls(origin, front);
    theFloorsAreFloors(origin, front);
    generateCatwalks(origin);
    theFloorIsLava(origin, front);
    createPathFromEachEntranceToTheCenterOverTheLiquid(origin, entrances);
    ceilingChan(origin, front);
    generateNetherPortalWithPlatform(origin, entrances);
    generateDoorways(origin, entrances, getSize());

    return null;
  }

  private void generateWalls(Coord origin, Direction front) {
    BlockBrush walls = levelSettings.getTheme().getPrimary().getWall();
    RectHollow.newRect(
        origin.copy().translate(front.left(), getSize()).translate(front, getSize()).copy().down(3),
        origin.copy().translate(front.right(), getSize()).translate(front.back(), getSize()).copy().up(getHeight())
    ).fill(worldEditor, walls);
  }

  private void theFloorsAreFloors(Coord origin, Direction front) {
    BlockBrush floors = levelSettings.getTheme().getPrimary().getFloor();
    RectSolid.newRect(
        origin.copy().translate(front.left(), 3).translate(front, 3).down(),
        origin.copy().translate(front.right(), 3).translate(front.back(), 3).down(2)
    ).fill(worldEditor, floors);
  }

  private void generateCatwalks(Coord origin) {
    BlockBrush floors = levelSettings.getTheme().getPrimary().getFloor();
    StairsBlock stair = levelSettings.getTheme().getPrimary().getStair();

    for (Direction side : Direction.cardinals()) {
      Coord catwalkOrigin = origin.copy().translate(side, getSize() - 1);
      RectSolid.newRect(
          catwalkOrigin.copy().translate(side.left(), getSize()),
          catwalkOrigin.copy().translate(side.right(), getSize()).translate(side.back()).down(2)
      ).fill(worldEditor, floors);

      RectSolid.newRect(
          catwalkOrigin.copy().translate(side.left(), 2),
          catwalkOrigin.copy().translate(side.right(), 2).translate(side.back())
      ).fill(worldEditor, SingleBlockBrush.AIR);

      for (Direction orthogonal : side.orthogonals()) {
        stair.setUpsideDown(false).setFacing(orthogonal.reverse());
        stair.stroke(worldEditor, catwalkOrigin.copy().translate(orthogonal, 3));
        stair.stroke(worldEditor, catwalkOrigin.copy().translate(orthogonal, 3).translate(side.back()));
      }
    }
  }

  private void theFloorIsLava(Coord origin, Direction front) {
    BlockBrush liquid = levelSettings.getTheme().getPrimary().getLiquid();
    RectSolid.newRect(
        origin.copy().translate(front, getSize() - 1).translate(front.left(), getSize() - 1).down(),
        origin.copy().translate(front.back(), getSize() - 1).translate(front.right(), getSize() - 1).down(2)
    ).fill(worldEditor, liquid, true, false);
  }

  private void createPathFromEachEntranceToTheCenterOverTheLiquid(Coord origin, List<Direction> entrances) {
    BlockBrush floors = levelSettings.getTheme().getPrimary().getFloor();
    Direction.cardinals().stream()
        .filter(direction -> !entrances.contains(direction))
        .forEach(direction -> RectSolid.newRect(
            origin.copy().translate(direction.left()).down(),
            origin.copy().translate(direction.right()).translate(direction, getSize()).down(2)
        ).fill(worldEditor, floors));
  }

  private void ceilingChan(Coord origin, Direction front) {
    // ceiling-chan >///<
    BlockBrush floors = levelSettings.getTheme().getPrimary().getFloor();
    RectSolid.newRect(
        origin.copy().translate(front.left(), getSize()).translate(front, getSize()).up(getHeight()),
        origin.copy().translate(front.right(), getSize()).translate(front.back(), getSize()).up(getHeight())
    ).fill(worldEditor, floors);
  }

  private void generateNetherPortalWithPlatform(Coord origin, List<Direction> entrances) {
    Direction front = entrances.get(0);

    BlockBrush pillar = levelSettings.getTheme().getPrimary().getPillar();
    RectSolid.newRect(
        origin.copy().translate(front).translate(front.left(), 3),
        origin.copy().translate(front.back()).translate(front.right(), 3).up(getHeight() - 1)
    ).fill(worldEditor, pillar);

    // portal platform
    RectSolid.newRect(
        origin.copy().translate(front.left(), 2).translate(front, 2),
        origin.copy().translate(front.right(), 2).translate(front.back(), 2)
    ).fill(worldEditor, pillar);

    StairsBlock stairsBrush = levelSettings.getTheme().getPrimary().getStair();
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
