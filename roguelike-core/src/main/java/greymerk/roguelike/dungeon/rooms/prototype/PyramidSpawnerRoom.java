package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.spawner.MobType.COMMON_MOBS;

public class PyramidSpawnerRoom extends BaseRoom {

  public PyramidSpawnerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    // fill air inside
    RectSolid.newRect(new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(worldEditor, SingleBlockBrush.AIR);


    // shell
    RectHollow.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4)).fill(worldEditor, walls(), false, true);
    RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(worldEditor, walls(), false, true);
    RectSolid.newRect(new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2)).fill(worldEditor, SingleBlockBrush.AIR);

    RectSolid.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4)).fill(worldEditor, theme().getPrimary().getFloor(), false, true);

    Coord cursor = origin.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    walls().stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    // Chests
    List<Coord> space = new ArrayList<>();

    for (Direction dir : Direction.CARDINAL) {

      // pillar
      cursor = origin.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      Coord start = cursor.copy();
      cursor.up(3);
      Coord end = cursor.copy();
      RectSolid.newRect(start, end).fill(worldEditor, pillars());
      cursor.up();
      walls().stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir, 2);
      walls().stroke(worldEditor, cursor);
      cursor.translate(dir.antiClockwise(), 2);
      walls().stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.up(5);
      cursor.translate(dir.antiClockwise());
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      cursor.up();
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);

      for (Direction orthogonals : dir.orthogonals()) {

        cursor = origin.copy();
        cursor.up(3);
        cursor.translate(orthogonals);
        cursor.translate(dir, 3);
        walls().stroke(worldEditor, cursor);

        cursor = origin.copy();
        cursor.up(4);
        cursor.translate(dir, 2);
        walls().stroke(worldEditor, cursor);

        cursor = origin.copy();
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        space.add(cursor);
      }
    }

    List<Coord> chestLocations = chooseRandomLocations(1, space);
    generateChests(chestLocations, getEntrance(entrances).reverse());
    final Coord cursor1 = origin.copy();
    generateSpawner(cursor1, COMMON_MOBS);
    return this;
  }

  public int getSize() {
    return 4;
  }
}
