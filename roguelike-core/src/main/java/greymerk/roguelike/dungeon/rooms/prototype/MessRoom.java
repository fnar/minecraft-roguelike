package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class MessRoom extends BaseRoom {

  public MessRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 9;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    // clear air
    Coord start = at.copy();
    start.translate(-8, -1, -8);
    Coord end = at.copy();
    end.translate(8, 5, 8);
    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    start = at.copy();
    start.translate(-2, 5, -2);
    end = at.copy();
    end.translate(2, 5, 2);
    RectSolid.newRect(start, end).fill(worldEditor, secondaryWallBrush(), false, true);

    Coord cursor = at.copy();
    cursor.up(4);
    primaryLightBrush().stroke(worldEditor, cursor);

    for (Direction dir : Direction.CARDINAL) {
      start = at.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end = start.copy();
      end.up(3);
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction d : Direction.CARDINAL) {
        cursor = end.copy();
        cursor.translate(d);
        primaryStairBrush().setUpsideDown(true).setFacing(d).stroke(worldEditor, cursor);
      }

      start = at.copy();
      start.translate(dir, 3);
      start.up(4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));


      Direction[] corners = new Direction[]{dir, dir.antiClockwise()};
      if (entrances.size() == 4 && dir == getEntrance(entrances)) {
        supplyCorner(corners, at, dir);
      } else {
        corner(corners, at);
      }

      generateWallDecor(dir, at);

      cursor = at.copy()
          .up(4)
          .translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    }

    List<Direction> nonDoors = new ArrayList<>();
    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        nonDoors.add(dir);
      }
    }

    Collections.shuffle(nonDoors);

    switch (nonDoors.size()) {
      case 3:
        sideTable(nonDoors.get(2), at);
      case 2:
        fireplace(nonDoors.get(1), at);
      case 1:
        supplies(getEntrance(nonDoors), at);
      default:
    }

    generateDoorways(at, entrances);

    return this;
  }

  private void supplyCorner(Direction[] corners, Coord origin, Direction entranceDir) {
    BlockBrush wall = theme().getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy().translate(corners[0], 7).translate(corners[1], 7);
    end = start.copy().up(4);
    wall.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy().translate(corners[0], 4).translate(corners[1], 4).up(4);
    secondaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    cursor = origin.copy()
        .translate(corners[0], 7)
        .translate(corners[1], 5)
        .up();

    generateChest(cursor, entranceDir.reverse(), ChestType.FOOD);

    cursor = origin.copy()
        .translate(corners[0], 5)
        .translate(corners[1], 7);
    BlockType.FURNACE.getBrush().setFacing(corners[1].reverse()).stroke(worldEditor, cursor);

    generateEntrywayWithDecor(corners, origin, true);
  }

  private void corner(Direction[] corners, Coord origin) {
    StairsBlock table = secondaryStairBrush();
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy()
        .translate(corners[0], 7)
        .translate(corners[1], 7);
    end = start.copy().up(4);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy()
        .translate(corners[0], 4)
        .translate(corners[1], 4)
        .up(4);
    secondaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    cursor = origin.copy()
        .translate(corners[0], 4)
        .translate(corners[1], 4);
    table.setUpsideDown(true).setFacing(corners[0].reverse()).stroke(worldEditor, cursor);
    cursor.translate(corners[1]);
    table.setUpsideDown(true).setFacing(corners[1]).stroke(worldEditor, cursor);
    cursor.translate(corners[0]);
    table.setUpsideDown(true).setFacing(corners[0]).stroke(worldEditor, cursor);
    cursor.translate(corners[1].reverse());
    table.setUpsideDown(true).setFacing(corners[1].reverse()).stroke(worldEditor, cursor);
    generateEntrywayWithDecor(corners, origin, false);
  }

  private void generateEntrywayWithDecor(Direction[] entrances, Coord origin, boolean areSomeStairsUpsideDown) {
    BlockSet primaryBlockSet = theme().getPrimary();
    BlockBrush wall = primaryBlockSet.getWall();
    BlockBrush pillar = primaryBlockSet.getPillar();
    StairsBlock stair = primaryBlockSet.getStair();

    for (Direction dir : entrances) {
      Coord cursor = origin.copy();
      cursor.translate(entrances[0], 3);
      cursor.translate(entrances[1], 3);
      cursor.translate(dir, 4);
      Coord start = cursor.copy();
      Coord end = cursor.copy();
      end.up(3);
      pillar.fill(worldEditor, RectSolid.newRect(start, end));
      cursor.up(3);
      cursor.translate(dir.reverse());
      wall.stroke(worldEditor, cursor);
      cursor.down();
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      start = cursor.copy();
      end = cursor.copy();
      end.translate(dir, 3);
      wall.fill(worldEditor, RectSolid.newRect(start, end));

      cursor = origin.copy();
      cursor.translate(entrances[0], 5);
      cursor.translate(entrances[1], 5);
      cursor.translate(dir, 2);
      start = cursor.copy();
      start.translate(dir.antiClockwise());
      end = cursor.copy();
      end.translate(dir.clockwise());
      stair.setUpsideDown(areSomeStairsUpsideDown).setFacing(dir.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.up(2);
      end.up(2);
      stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(worldEditor, start);
      stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(worldEditor, end);
      start.up();
      end.up();
      wall.fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(dir);
      end.translate(dir);
      end.down(3);
      RectSolid.newRect(start, end).fill(worldEditor, secondaryWallBrush(), false, true);
    }
  }

  private void generateWallDecor(Direction dir, Coord origin) {

    Coord start = origin.copy().translate(dir, 7).up(3).translate(dir.left(), 2);
    Coord end = origin.copy().translate(dir, 7).up(4).translate(dir.right(), 2);
    primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor;
    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o, 2);
      cursor.up(2);
      primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);

      cursor.translate(dir.reverse());
      cursor.up(2);
      primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(dir, 6);
    cursor.up(3);
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.up();
    primaryWallBrush().stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    start = origin.copy();
    start.up(5);
    start.translate(dir, 4);
    end = start.copy();
    end.translate(dir);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(worldEditor, secondaryWallBrush(), false, true);
  }

  private void fireplace(Direction dir, Coord origin) {
    RectSolid behindMantle = RectSolid.newRect(
        origin.copy().translate(dir, 7).translate(dir.left(), 2),
        origin.copy().translate(dir, 8).translate(dir.right(), 2).up(2));
    primaryWallBrush().fill(worldEditor, behindMantle);

    RectSolid fireArea = RectSolid.newRect(
        origin.copy().translate(dir, 7).translate(dir.left()),
        origin.copy().translate(dir, 7).translate(dir.right()).up());
    SingleBlockBrush.AIR.fill(worldEditor, fireArea);

    Coord cursor = origin.copy();
    cursor.translate(dir, 6);
    BlockType.IRON_BAR.getBrush().stroke(worldEditor, cursor);
    cursor.translate(dir);
    cursor.down();
    BlockType.NETHERRACK.getBrush().stroke(worldEditor, cursor);
    cursor.up();
    BlockType.FIRE.getBrush().stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    primaryStairBrush().setUpsideDown(false).setFacing(dir.reverse()).stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy().translate(dir, 6).translate(o);
      BlockType.IRON_BAR.getBrush().stroke(worldEditor, cursor);
      cursor.translate(o);
      theme().getPrimary().getWall().stroke(worldEditor, cursor);
      cursor.up();
      primaryStairBrush().setUpsideDown(false).setFacing(o).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.up().translate(dir.reverse());
      primaryStairBrush().setUpsideDown(false).setFacing(o).stroke(worldEditor, cursor);

      cursor = origin.copy().translate(dir, 6).translate(o);
      BlockType.IRON_BAR.getBrush().stroke(worldEditor, cursor);
      cursor.translate(dir).down();
      BlockType.NETHERRACK.getBrush().stroke(worldEditor, cursor);
      cursor.up();
      BlockType.FIRE.getBrush().stroke(worldEditor, cursor);
    }
  }

  private void supplies(Direction dir, Coord origin) {
    Coord cursor = origin.copy();
    cursor.translate(dir, 7);
    theme().getPrimary().getStair().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.up();
    generateChest(cursor, dir, ChestType.FOOD);
    cursor.translate(dir.antiClockwise());
    BlockType.FURNACE.getBrush().setFacing(dir).stroke(worldEditor, cursor);
    cursor.translate(dir.clockwise(), 2);
    BlockType.CRAFTING_TABLE.getBrush().stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      cursor.translate(o);
      theme().getPrimary().getStair().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o);
      theme().getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      theme().getPrimary().getStair().setUpsideDown(false).setFacing(o.reverse()).stroke(worldEditor, cursor);
    }


  }

  private void sideTable(Direction dir, Coord origin) {
    StairsBlock table = secondaryStairBrush();
    Coord cursor = origin.copy();

    cursor.translate(dir, 5);
    table.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.translate(dir);
    table.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(o);
      table.setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
      cursor.translate(dir);
      table.setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
    }
  }

}
