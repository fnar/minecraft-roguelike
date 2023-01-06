package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DarkHallRoom extends BaseRoom {

  public DarkHallRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 9;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    RectHollow r1 = RectHollow.newRect(
        at.copy().north(7).west(7).down(),
        at.copy().south(7).east(7).up(7));
    primaryWallBrush().fill(worldEditor, r1, false, true);

    RectHollow r2 = RectHollow.newRect(
        at.copy().north(4).west(4).up(6),
        at.copy().south(4).east(4).up(9));
    primaryWallBrush().fill(worldEditor, r2, false, true);

    RectSolid r3 = RectSolid.newRect(
        at.copy().north(6).west(6).down(),
        at.copy().south(6).east(6).down());
    primaryFloorBrush().fill(worldEditor, r3, false, true);

    for (Direction entrance : entrances) {
      RectSolid r4 = RectSolid.newRect(
          at.copy().translate(entrance.left()),
          at.copy().translate(entrance.right()).translate(entrance, 7));
      secondaryFloorBrush().fill(worldEditor, r4, false, true);
    }

    for (Direction side : Direction.CARDINAL) {
      generateAccentPillar(at.copy().translate(side, 6).translate(side.left(), 6), 5);
      generateBeam(side, at.copy().translate(side, 6).up(6), 13);
      generateBeam(side, at.copy().translate(side, 3).up(6), 7);
      generateBeam(side, at.copy().translate(side, 3).up(8), 7);
      generateBeam(side, at.copy().up(8), 7);

      secondaryPillarBrush().stroke(worldEditor, at.copy().translate(side, 3).up(7));

      if (!entrances.contains(side)) {
        pillar(side.reverse(), at.copy().translate(side, 6));
      } else {
        generateEntranceArchway(at.copy().translate(side, 7), side);
      }

      secondaryWallBrush().fill(worldEditor, RectSolid.newRect(
          at.copy().translate(side, 6).up(6),
          at.copy().translate(side, 4).up(6)));

      for (Direction orthogonal : side.orthogonals()) {
        Coord cursor = at.copy().translate(side, 6).translate(orthogonal, 3);
        pillar(side.reverse(), cursor);
        secondaryWallBrush().fill(worldEditor, RectSolid.newRect(
            cursor.copy().up(6),
            cursor.copy().up(6).translate(side.reverse(), 6)));
      }
    }

    generateDoorways(at, entrances);

    return this;
  }

  private void generateEntranceArchway(Coord origin, Direction facing) {
    Coord aboveOrigin = origin.copy().up(2);

    secondaryWallBrush().fill(worldEditor, RectSolid.newRect(
        aboveOrigin.copy().translate(facing.left(), 2),
        aboveOrigin.copy().translate(facing.right(), 2).up(3)));

    SingleBlockBrush.AIR.stroke(worldEditor, aboveOrigin);

    for (Direction orthogonal : facing.orthogonals()) {
      secondaryStairBrush().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, aboveOrigin.copy().translate(orthogonal));
      pillar(orthogonal.reverse(), origin.copy().translate(facing.back()).translate(orthogonal, 3));
      Coord cursor = origin.copy().translate(orthogonal, 2);
      secondaryPillarBrush().stroke(worldEditor, cursor);
      secondaryPillarBrush().stroke(worldEditor, cursor.up());
    }
  }

  private void generateBeam(Direction dir, Coord origin, int width) {
    BlockBrush wall = secondaryWallBrush();
    int left = width / 2;
    int right = width - left - 1;
    Coord beamLeftAnchor = origin.copy().translate(dir.left(), left);
    Coord beamRightAnchor = origin.copy().translate(dir.right(), right);
    RectSolid beam = RectSolid.newRect(beamLeftAnchor, beamRightAnchor);
    wall.fill(worldEditor, beam);
  }

  public void generateAccentPillar(Coord origin, int height) {
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(
        origin.copy(),
        origin.copy().up(height)
    ));
  }

  private void pillar(Direction dir, Coord origin) {
    generateAccentPillar(origin, 5);

    Coord cursor = origin.copy().up(3).translate(dir);
    secondaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    secondaryStairBrush().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor.up());
    secondaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor.translate(dir));
    secondaryStairBrush().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor.up());
    cursor.translate(dir);
    if (worldEditor.isAirBlock(cursor)) {
      secondaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    } else {
      secondaryWallBrush().stroke(worldEditor, cursor);
    }
  }

}
