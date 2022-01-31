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
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    RectHollow r1 = RectHollow.newRect(
        origin.copy().north(7).west(7).down(),
        origin.copy().south(7).east(7).up(7));
    walls().fill(worldEditor, r1, false, true);

    RectHollow r2 = RectHollow.newRect(
        origin.copy().north(4).west(4).up(6),
        origin.copy().south(4).east(4).up(9));
    walls().fill(worldEditor, r2, false, true);

    RectSolid r3 = RectSolid.newRect(
        origin.copy().north(6).west(6).down(),
        origin.copy().south(6).east(6).down());
    floors().fill(worldEditor, r3, false, true);

    for (Direction entrance : entrances) {
      RectSolid r4 = RectSolid.newRect(
          origin.copy().translate(entrance.left()),
          origin.copy().translate(entrance.right()).translate(entrance, 7));
      secondaryFloors().fill(worldEditor, r4, false, true);
    }

    for (Direction side : Direction.CARDINAL) {
      generateAccentPillar(origin.copy().translate(side, 6).translate(side.left(), 6), 5);
      generateBeam(side, origin.copy().translate(side, 6).up(6), 13);
      generateBeam(side, origin.copy().translate(side, 3).up(6), 7);
      generateBeam(side, origin.copy().translate(side, 3).up(8), 7);
      generateBeam(side, origin.copy().up(8), 7);

      secondaryPillars().stroke(worldEditor, origin.copy().translate(side, 3).up(7));

      if (!entrances.contains(side)) {
        pillar(side.reverse(), origin.copy().translate(side, 6));
      } else {
        generateEntranceArchway(origin.copy().translate(side, 7), side);
      }

      secondaryWalls().fill(worldEditor, RectSolid.newRect(
          origin.copy().translate(side, 6).up(6),
          origin.copy().translate(side, 4).up(6)));

      for (Direction orthogonal : side.orthogonals()) {
        Coord cursor = origin.copy().translate(side, 6).translate(orthogonal, 3);
        pillar(side.reverse(), cursor);
        secondaryWalls().fill(worldEditor, RectSolid.newRect(
            cursor.copy().up(6),
            cursor.copy().up(6).translate(side.reverse(), 6)));
      }
    }

    generateDoorways(origin, entrances);

    return this;
  }

  private void generateEntranceArchway(Coord origin, Direction facing) {
    Coord aboveOrigin = origin.copy().up(2);

    secondaryWalls().fill(worldEditor, RectSolid.newRect(
        aboveOrigin.copy().translate(facing.left(), 2),
        aboveOrigin.copy().translate(facing.right(), 2).up(3)));

    SingleBlockBrush.AIR.stroke(worldEditor, aboveOrigin);

    for (Direction orthogonal : facing.orthogonals()) {
      secondaryStairs().setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, aboveOrigin.copy().translate(orthogonal));
      pillar(orthogonal.reverse(), origin.copy().translate(facing.back()).translate(orthogonal, 3));
      Coord cursor = origin.copy().translate(orthogonal, 2);
      secondaryPillars().stroke(worldEditor, cursor);
      secondaryPillars().stroke(worldEditor, cursor.up());
    }
  }

  private void generateBeam(Direction dir, Coord origin, int width) {
    BlockBrush wall = secondaryWalls();
    int left = width / 2;
    int right = width - left - 1;
    Coord beamLeftAnchor = origin.copy().translate(dir.left(), left);
    Coord beamRightAnchor = origin.copy().translate(dir.right(), right);
    RectSolid beam = RectSolid.newRect(beamLeftAnchor, beamRightAnchor);
    wall.fill(worldEditor, beam);
  }

  public void generateAccentPillar(Coord origin, int height) {
    RectSolid.newRect(
        origin.copy(),
        origin.copy().up(height)
    ).fill(worldEditor, pillars());
  }

  private void pillar(Direction dir, Coord origin) {
    generateAccentPillar(origin, 5);

    Coord cursor = origin.copy().up(3).translate(dir);
    secondaryStairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    secondaryStairs().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor.up());
    secondaryStairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor.translate(dir));
    secondaryStairs().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor.up());
    cursor.translate(dir);
    if (worldEditor.isAirBlock(cursor)) {
      secondaryStairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    } else {
      secondaryWalls().stroke(worldEditor, cursor);
    }
  }

  @Override
  public int getSize() {
    return 10;
  }


}
