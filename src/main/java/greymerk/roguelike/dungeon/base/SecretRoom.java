package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.ToString;

@ToString
public class SecretRoom {

  private final RoomSetting roomSetting;

  public SecretRoom(RoomSetting roomSetting) {
    this.roomSetting = roomSetting;
  }

  private boolean isValid(IWorldEditor editor, Cardinal dir, Coord pos, IDungeonRoom prototype) {
    if (roomSetting.getCount() <= 0) {
      return false;
    }
    Coord cursor = new Coord(pos);
    cursor.add(dir, prototype.getSize() + 5);

    return prototype.validLocation(editor, dir, cursor);
  }

  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos) {
    IDungeonRoom prototype = roomSetting.instantiate();
    if (!isValid(editor, dir, pos, prototype)) {
      return null;
    }

    int size = prototype.getSize();

    Coord start = new Coord(pos);
    Coord end = new Coord(pos);
    start.add(dir.orthogonal()[0]);
    start.add(Cardinal.DOWN);
    start.add(dir, 2);
    end.add(dir.orthogonal()[1]);
    end.add(dir, size + 5);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getWall(), false, true);

    end = new Coord(pos);
    end.add(dir, size + 5);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, pos, end, BlockType.get(BlockType.AIR));

    end.add(Cardinal.DOWN);
    prototype.generate(editor, rand, settings, new Cardinal[]{dir}, end);
    return prototype;
  }

  @Override
  public boolean equals(Object o) {

    SecretRoom other = (SecretRoom) o;

    if (roomSetting.getRoomType() != other.roomSetting.getRoomType()) {
      return false;
    }

    return roomSetting.getCount() == other.roomSetting.getCount();
  }
}
