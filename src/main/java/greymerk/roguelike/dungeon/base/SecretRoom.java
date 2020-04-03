package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SecretRoom extends DungeonBase {

  private final RoomSetting roomSetting;

  public SecretRoom(RoomSetting roomSetting) {
    this.roomSetting = roomSetting;
  }

  boolean isValid(IWorldEditor editor, Cardinal dir, Coord pos) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    IDungeonRoom prototype = createPrototype();
    Coord cursor = new Coord(pos);
    cursor.add(dir, prototype.getSize() + 5);
    return prototype.validLocation(editor, dir, cursor);
  }

  // todo: Match the signature of DungeonBase
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord pos, Cardinal... dir) {
    IDungeonRoom prototype = createPrototype();

    int size = prototype.getSize();

    Coord start = new Coord(pos);
    Coord end = new Coord(pos);
    Cardinal entrance = dir[0];
    start.add(entrance.orthogonal()[0]);
    start.add(Cardinal.DOWN);
    start.add(entrance, 2);
    end.add(entrance.orthogonal()[1]);
    end.add(entrance, size + 5);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getWall(), false, true);

    end = new Coord(pos);
    end.add(entrance, size + 5);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, pos, end, BlockType.get(BlockType.AIR));

    end.add(Cardinal.DOWN);
    prototype.generate(editor, rand, settings, end, entrance);
    return prototype;
  }

  @Override
  public int getSize() {
    return createPrototype().getSize();
  }

  private IDungeonRoom createPrototype() {
    return getRoomSetting().instantiate();
  }

  public RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
