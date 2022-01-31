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

public class PyramidCornerRoom extends BaseRoom {

  public PyramidCornerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    RectHollow.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(worldEditor, walls(), false, true);
    RectSolid.newRect(new Coord(x - 2, y + 3, z - 2), new Coord(x + 2, y + 5, z + 2)).fill(worldEditor, walls(), false, true);
    RectSolid.newRect(new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 3, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);

    // floor
    RectSolid.newRect(new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3)).fill(worldEditor, theme().getPrimary().getFloor(), false, true);

    Coord cursor = origin.copy();
    cursor.up(4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);


    for (Direction dir : Direction.CARDINAL) {

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(dir.orthogonals()[0], 2);
      Coord start = cursor.copy();
      cursor.up(2);
      Coord end = cursor.copy();
      RectSolid.newRect(start, end).fill(worldEditor, pillars());
    }

    return this;
  }

  public int getSize() {
    return 4;
  }

}
