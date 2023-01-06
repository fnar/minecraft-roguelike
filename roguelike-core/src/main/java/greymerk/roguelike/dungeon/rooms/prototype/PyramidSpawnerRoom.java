package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;

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

  public BaseRoom generate(Coord at, List<Direction> entrances) {

    int x = at.getX();
    int y = at.getY();
    int z = at.getZ();

    // fill air inside
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3)));


    // shell
    RectHollow.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4)).fill(worldEditor, primaryWallBrush(), false, true);
    RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(worldEditor, primaryWallBrush(), false, true);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2)));

    RectSolid.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4)).fill(worldEditor, theme().getPrimary().getFloor(), false, true);

    Coord cursor = at.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    primaryWallBrush().stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    for (Direction dir : Direction.CARDINAL) {

      // pillar
      cursor = at.copy();
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      Coord start = cursor.copy();
      cursor.up(3);
      Coord end = cursor.copy();
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));
      cursor.up();
      primaryWallBrush().stroke(worldEditor, cursor);

      cursor = at.copy();
      cursor.up(4);
      cursor.translate(dir, 2);
      primaryWallBrush().stroke(worldEditor, cursor);
      cursor.translate(dir.antiClockwise(), 2);
      primaryWallBrush().stroke(worldEditor, cursor);

      cursor = at.copy();
      cursor.up(5);
      cursor.translate(dir.antiClockwise());
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      cursor.up();
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);

      for (Direction orthogonals : dir.orthogonals()) {

        cursor = at.copy();
        cursor.up(3);
        cursor.translate(orthogonals);
        cursor.translate(dir, 3);
        primaryWallBrush().stroke(worldEditor, cursor);

        cursor = at.copy();
        cursor.up(4);
        cursor.translate(dir, 2);
        primaryWallBrush().stroke(worldEditor, cursor);
      }
    }

    generateChest(generateChestLocation(at), getEntrance(entrances).reverse());
    final Coord cursor1 = at.copy();
    generateSpawner(cursor1, COMMON_MOBS);
    return this;
  }

  public int getSize() {
    return 4;
  }
}
