package greymerk.roguelike.dungeon.rooms.prototype;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Direction.CARDINAL;
import static greymerk.roguelike.worldgen.Direction.UP;

public class BrickRoom extends BaseRoom {

  public BrickRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    // fill air inside
    RectSolid.newRect(new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);

    // shell
    RectHollow.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4)).fill(worldEditor, walls(), false, true);

    RectSolid.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4)).fill(worldEditor, floors(), false, true);


    Coord cursor = new Coord(x, y, z);
    cursor.translate(UP, 5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(UP, 1);
    walls().stroke(worldEditor, cursor);


    for (Direction dir : CARDINAL) {

      // top
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 1);
      cursor.translate(UP, 5);
      stairs().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor, false, true);
      cursor.translate(dir.antiClockwise(), 1);
      walls().stroke(worldEditor, cursor, false, true);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      cursor.translate(UP, 1);
      walls().stroke(worldEditor, cursor, false, true);

      // pillar
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      Coord start = cursor.copy();
      cursor.translate(UP, 2);
      Coord end = cursor.copy();
      RectSolid.newRect(start, end).fill(worldEditor, pillars());
      cursor.translate(UP, 1);
      walls().stroke(worldEditor, cursor);

      // pillar stairs
      for (Direction orthogonals : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        cursor.translate(UP, 3);
        stairs().setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(worldEditor, cursor);
      }

      // layer above pillars
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      cursor.translate(UP, 4);
      walls().stroke(worldEditor, cursor, false, true);

      for (Direction orthogonals : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(UP, 4);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 1);
        stairs().setUpsideDown(true).setFacing(orthogonals.reverse()).stroke(worldEditor, cursor, false, true);
      }

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 1);
      cursor.translate(dir.antiClockwise(), 1);
      cursor.translate(UP, 5);
      walls().stroke(worldEditor, cursor, false, true);
    }

    Direction randomDirection = Direction.randomCardinal(random());
    Coord spawnerLocation = origin.copy()
        .translate(randomDirection, random().nextInt(getSize()))
        .translate(randomDirection.clockwise(), random().nextInt(getSize()));
    generateSpawner(spawnerLocation);

    generateChest(origin, spawnerLocation, getEntrance(entrances));

    return this;
  }

  public void generateChest(Coord origin, Coord spawnerLocation, Direction facing) {
    int difficulty = levelSettings.getDifficulty(origin);

    boolean isChestBeneathSpawner = random().nextInt(Math.max(1, difficulty) + 1) != 0;
    List<Coord> chestLocations = isChestBeneathSpawner
        ? Lists.newArrayList(spawnerLocation.copy().down())
        : chooseRandomLocations(1, getPotentialSpawnLocations(origin));

    generateTrappableChests(chestLocations, facing);
  }

  public List<Coord> getPotentialSpawnLocations(Coord origin) {
    List<Coord> potentialChestLocations = new ArrayList<>();
    CARDINAL.forEach(dir -> Arrays.stream(dir.orthogonals())
        .map(orthogonal -> origin.copy()
            .translate(dir, 3)
            .translate(orthogonal, 2))
        .forEach(potentialChestLocations::add));
    return potentialChestLocations;
  }

  public int getSize() {
    return 6;
  }
}
