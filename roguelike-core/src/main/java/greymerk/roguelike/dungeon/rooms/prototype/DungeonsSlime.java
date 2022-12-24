package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.worldgen.generatables.Pillar;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSlime extends BaseRoom {

  public DungeonsSlime(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Coord start = origin.copy().translate(new Coord(-8, -1, -8));
    Coord end = origin.copy().translate(new Coord(8, 5, 8));
    walls().fill(worldEditor, RectHollow.newRect(start, end), false, true);

    generateCorners(origin);
    generateWalls(origin);
    generateWaterways(origin, entrances);
    generateRailings(origin, entrances);
    generatePipes(origin, entrances);

    return this;
  }

  private void generateCorners(Coord origin) {
    for (Direction dir : Direction.CARDINAL) {
      Coord cornerCoord = origin.copy();
      cornerCoord.translate(dir, 5);
      cornerCoord.translate(dir.antiClockwise(), 5);
      generateCorner(cornerCoord);
    }
  }

  private void generateCorner(Coord origin) {
    generateCornerPool(origin);
    generateCornerPillars(origin);
    generateCornerExternalRailings(origin);
  }

  private void generateCornerPool(Coord origin) {
    Coord start = origin.copy().translate(-1, -1, -1);
    Coord end = origin.copy().translate(1, -1, 1);
    RectSolid poolCoords = RectSolid.newRect(start, end);
    liquid().fill(worldEditor, poolCoords);
    poolCoords.translate(Direction.DOWN, 1);
    walls().fill(worldEditor, poolCoords);
  }

  private void generateCornerPillars(Coord origin) {
    for (Direction dir : Direction.CARDINAL) {
      Coord start = origin.copy()
          .translate(dir, 2)
          .translate(dir.antiClockwise(), 2);

      Pillar.newPillar(worldEditor)
          .withPillarBrush(pillars())
          .withStairBrush(stairs())
          .withHeight(4)
          .generate(start);
    }
  }

  private void generateCornerExternalRailings(Coord origin) {
    for (Direction dir: Direction.CARDINAL) {
      Coord start = origin.copy().translate(dir, 2).translate(dir.antiClockwise());
      Coord end = origin.copy().translate(dir, 2).translate(dir.clockwise());
      RectSolid railingCoords = RectSolid.newRect(start, end);
      BlockType.IRON_BAR.getBrush().fill(worldEditor, railingCoords);
    }
  }

  private void generateWalls(Coord origin) {
    for (Direction dir : Direction.CARDINAL) {
      Coord start = origin.copy().up(4).translate(dir, 3).translate(dir.antiClockwise(), 8);
      Coord end = origin.copy().up(4).translate(dir, 3).translate(dir.clockwise(), 8);
      RectSolid wall = RectSolid.newRect(start, end);
      walls().fill(worldEditor, wall);

      wall.translate(dir, 4);
      walls().fill(worldEditor, wall);
    }
  }

  private void generateWaterways(Coord origin, List<Direction> entrances) {
    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        generateWaterway(origin, dir);
      }
    }
  }

  private void generateWaterway(Coord origin, Direction dir) {
    Coord start = origin.copy().translate(dir, 4).translate(dir.antiClockwise(), 3);
    Coord end = origin.copy().translate(dir, 6).translate(dir.clockwise(), 3);

    RectSolid waterway = RectSolid.newRect(start, end);
    SingleBlockBrush.AIR.fill(worldEditor, waterway);

    waterway.translate(Direction.DOWN, 1);
    liquid().fill(worldEditor, waterway);

    waterway.translate(Direction.DOWN, 1);
    walls().fill(worldEditor, waterway);
  }

  private void generatePipes(Coord origin, List<Direction> entrances) {
    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        generatePipe(origin, dir);
      }
    }
  }

  private void generatePipe(Coord origin, Direction dir) {
    Coord cursor = origin.copy();
    cursor.translate(dir, 7);
    walls().stroke(worldEditor, cursor);

    cursor.up(2);
    walls().stroke(worldEditor, cursor);

    cursor.down();
    cursor.translate(dir);
    liquid().stroke(worldEditor, cursor);

    generatePipeOpening(origin, dir);
  }

  private void generatePipeOpening(Coord origin, Direction dir) {
    for (Direction o : dir.orthogonals()) {
      Coord cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o);
      stairs().setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
      cursor.up();
      walls().stroke(worldEditor, cursor);
      cursor.up();
      stairs().setUpsideDown(false).setFacing(o).stroke(worldEditor, cursor);
    }
  }

  private void generateRailings(Coord origin, List<Direction> entrances) {
    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        generateRailing(origin, dir);
      }
    }
  }

  private void generateRailing(Coord origin, Direction dir) {
    Coord start = origin.copy().translate(dir, 3);
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    BlockType.IRON_BAR.getBrush().fill(worldEditor, RectSolid.newRect(start, end));
  }

  public int getSize() {
    return 8;
  }
}
