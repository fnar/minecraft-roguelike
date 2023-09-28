package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class LinkerRoom extends BaseRoom {

  public LinkerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 4;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    generateWalls(at);
    generateCeiling(at);
    generateFloors(at);
    Direction.CARDINAL.forEach(dir -> generateGrate(at, dir));
    Direction.CARDINAL.forEach(dir -> generateCorner(at, dir));
    return this;
  }

  private void generateWalls(Coord at) {
    RectHollow.newRect(
            at.copy().translate(new Coord(-4, -1, -4)),
            at.copy().translate(new Coord(4, 9, 4)))
        .fill(worldEditor, primaryWallBrush(), false, true);
  }

  private void generateCeiling(Coord at) {
    primaryWallBrush()
        .fill(worldEditor, RectSolid.newRect(
            at.copy().translate(new Coord(-4, 9, -4)),
            at.copy().translate(new Coord(4, 9, 4))));
  }

  private void generateFloors(Coord at) {
    primaryFloorBrush()
        .fill(worldEditor, RectSolid.newRect(
            at.copy().translate(new Coord(-4, -1, -4)),
            at.copy().translate(new Coord(4, -1, 4))));
  }

  private void generateGrate(Coord at, Direction dir) {
    Coord start = at.copy().translate(dir, 4).down().translate(dir.antiClockwise(), 4);
    Coord end = at.copy().translate(dir, 4).up(8).translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(worldEditor, BlockType.IRON_BAR.getBrush(), true, false);
  }

  private void generateCorner(Coord at, Direction dir) {
    Coord start = at.copy().translate(dir, 3).translate(dir.antiClockwise(), 3);
    Coord end = at.copy().translate(dir, 4).translate(dir.antiClockwise(), 4).up(8);
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));
  }

}
