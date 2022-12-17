package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.decorative.BedBlock;
import com.github.fnar.minecraft.block.decorative.FlowerPotBlock;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class BedRoomRoom extends BaseRoom {

  public BedRoomRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public void pillar(final Coord base, int height) {
    Coord bottom = base.copy();
    Coord top = base.copy().up(height);
    RectSolid pillar = RectSolid.newRect(bottom, top);
    pillars().fill(worldEditor, pillar);

    top.down();
    for (Direction cardinal: Direction.CARDINAL) {
      Coord support = top.copy().translate(cardinal);
      secondaryStairs().setUpsideDown(true).setFacing(cardinal).stroke(worldEditor, support, true, false);
    }
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Direction entranceDirection = getEntrance(entrances);
    generateWalls(origin, entranceDirection);
    generateFloorDecorationPattern(origin, entranceDirection);
    generateShelves(origin, entranceDirection);
    generateCeiling(origin, entranceDirection);
    generatePillars(origin, entranceDirection);
    generateCrossbeams(origin, entranceDirection);
    generateBed(origin, entranceDirection);
    generateChestz(origin, entranceDirection);
    generateCraftingTable(origin, entranceDirection);
    generateFurnace(origin, entranceDirection);

    return this;
  }

  private void generateWalls(Coord origin, Direction entranceDirection) {
    Coord start = origin.copy()
        .translate(entranceDirection.reverse().antiClockwise(), 4)
        .translate(entranceDirection, 4)
        .down();
    Coord end = origin.copy()
        .translate(entranceDirection.reverse().clockwise(), 4)
        .translate(entranceDirection.reverse(), 4)
        .up(4);
    RectHollow walls = RectHollow.newRect(start, end);
    walls().fill(worldEditor, walls, false, true);
  }

  private void generateFloorDecorationPattern(Coord origin, Direction entranceDirection) {
    Coord start = origin.copy()
        .down()
        .translate(entranceDirection.antiClockwise(), 1)
        .translate(entranceDirection, 2);
    Coord end = origin.copy()
        .down()
        .translate(entranceDirection.clockwise(), 1)
        .translate(entranceDirection.reverse(), 2);
    RectSolid floorPattern = RectSolid.newRect(start, end);
    secondaryWalls().fill(worldEditor, floorPattern);
  }

  private void generateCeiling(Coord origin, Direction entranceDirection) {
    for (Direction outward : entranceDirection.orthogonals()) {
      BlockBrush stair = secondaryStairs().setUpsideDown(true).setFacing(outward.reverse());

      Coord rowStart = origin.copy().translate(outward, 3).translate(outward.antiClockwise(), 2);
      Coord rowEnd = rowStart.copy().translate(outward.clockwise(), 4);
      RectSolid row = RectSolid.newRect(rowStart, rowEnd);
      row.translate(Direction.UP, 2);
      stair.fill(worldEditor, row);

      row.translate(Direction.UP, 1);
      walls().fill(worldEditor, row);

      row.translate(outward.reverse(), 1);
      stair.fill(worldEditor, row);
    }
  }

  private void generateCrossbeams(Coord origin, Direction entranceDirection) {
    Coord beamCenter = origin.copy()
        .up(3)
        .translate(entranceDirection, 3);

    Coord beamStart = beamCenter.copy().translate(entranceDirection.antiClockwise(), 2);
    Coord beamEnd = beamCenter.copy().translate(entranceDirection.clockwise(), 2);
    RectSolid beam = RectSolid.newRect(beamStart, beamEnd);

    for (int i = 0; i < 3; i++) {
      secondaryWalls().fill(worldEditor, beam);
      beam.translate(entranceDirection.reverse(), 3);
    }
  }

  private void generateShelves(Coord origin, Direction entranceDirection) {
    for (Direction outward : entranceDirection.orthogonals()) {
      BlockBrush stair = secondaryStairs().setUpsideDown(true).setFacing(outward.reverse());

      Coord shelfStart = origin.copy().translate(outward, 3).translate(outward.antiClockwise(), 2);
      Coord shelfEnd = shelfStart.copy().translate(outward.clockwise(), 4);
      RectSolid shelves = RectSolid.newRect(shelfStart, shelfEnd);
      stair.fill(worldEditor, shelves);
    }
  }

  private void generatePillars(Coord origin, Direction entranceDirection) {
    for (Direction orthogonal : entranceDirection.orthogonals()) {
      Coord cursor = origin.copy();
      cursor.translate(orthogonal, 3);
      pillar(cursor, 3);
      for (Direction p : orthogonal.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(p, 3);
        pillar(c, 3);
      }
    }
  }

  private void generateBed(Coord origin, Direction entranceDirection) {
    Direction side = random().nextBoolean() ? entranceDirection.antiClockwise() : entranceDirection.clockwise();

    Coord cursor = origin.copy();
    cursor.translate(entranceDirection.reverse(), 3);
    BedBlock.bed().setColor(DyeColor.chooseRandom(random())).setFacing(entranceDirection).stroke(worldEditor, cursor);

    // bookshelf with flowerpot atop
    cursor.translate(side, 2);
    BlockType.BOOKSHELF.getBrush().stroke(worldEditor, cursor);
    cursor.up();
    FlowerPotBlock.flowerPot().withRandomContent(random()).stroke(worldEditor, cursor);

    // bedside nightstand with torch atop
    cursor.translate(side.reverse(), 3);
    cursor.down();
    secondaryStairs().setUpsideDown(true).setFacing(entranceDirection).stroke(worldEditor, cursor);
    cursor.up();
    TorchBlock.torch().setFacing(Direction.UP).stroke(worldEditor, cursor);
  }

  private void generateChestz(Coord origin, Direction entranceDirection) {
    Direction side = entranceDirection.reverse().orthogonals()[random().nextBoolean() ? 1 : 0];
    Coord cursor = origin.copy()
        .translate(entranceDirection.reverse())
        .translate(side, 3);

    Coord chestLocation = cursor.add(Direction.UP);
    generateChest(chestLocation, side, ChestType.STARTER);
  }

  private void generateCraftingTable(Coord origin, Direction entranceDirection) {
    Direction side = entranceDirection.reverse().orthogonals()[random().nextBoolean() ? 1 : 0];
    Coord cursor = origin.copy()
        .translate(entranceDirection.reverse())
        .translate(side, 3)
        .translate(side.reverse(), 6);

    if (random().nextBoolean()) {
      generateTorchAbove(cursor);

      cursor.translate(entranceDirection.reverse());
      BlockType.CRAFTING_TABLE.getBrush().stroke(worldEditor, cursor);
    } else {
      BlockType.CRAFTING_TABLE.getBrush().stroke(worldEditor, cursor);
      cursor.translate(entranceDirection.reverse());

      generateTorchAbove(cursor);
    }
  }

  private void generateTorchAbove(Coord cursor) {
    cursor.up();
    TorchBlock.torch().setFacing(Direction.UP).stroke(worldEditor, cursor);
    cursor.down();
  }

  private void generateFurnace(Coord origin, Direction entranceDirection) {
    Direction side = random().nextBoolean() ? entranceDirection.antiClockwise() : entranceDirection.clockwise();

    Coord furnace = origin.copy()
        .translate(entranceDirection)
        .translate(side, 3)
        .translate(entranceDirection, random().nextBoolean() ? 1 : 0);

    BlockType.FURNACE.getBrush().setFacing(side.reverse()).stroke(worldEditor, furnace);

    RldItemStack coal = Material.Type.COAL.asItemStack().withCount(2 + random().nextInt(3));

    worldEditor.setItem(furnace, WorldEditor.FURNACE_FUEL_SLOT, coal);
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public boolean isValidLocation(Direction dir, Coord pos) {
    Coord start;
    Coord end;

    start = pos.copy();
    end = start.copy();
    start.translate(dir.reverse(), 5);
    end.translate(dir, 5);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    start.down();
    end.up(3);

    for (Coord c : new RectHollow(start, end)) {
      if (worldEditor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }
}
