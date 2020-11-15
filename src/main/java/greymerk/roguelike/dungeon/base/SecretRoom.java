package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class SecretRoom extends DungeonBase {

  public SecretRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public boolean isValid(WorldEditor editor, Cardinal dir, Coord pos) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    DungeonBase prototype = createPrototype();
    Coord cursor = new Coord(pos);
    cursor.translate(dir, prototype.getSize() + 5);
    return prototype.validLocation(editor, dir, cursor);
  }

  // todo: Match the signature of DungeonBase
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Cardinal... dir) {
    DungeonBase prototype = createPrototype();

    int size = prototype.getSize();

    Coord start = new Coord(pos);
    Coord end = new Coord(pos);
    Cardinal entrance = dir[0];
    start.translate(entrance.orthogonal()[0]);
    start.translate(Cardinal.DOWN);
    start.translate(entrance, 2);
    end.translate(entrance.orthogonal()[1]);
    end.translate(entrance, size + 5);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getWall(), false, true);

    end = new Coord(pos);
    end.translate(entrance, size + 5);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, pos, end, BlockType.get(BlockType.AIR));

    end.translate(Cardinal.DOWN);
    prototype.generate(editor, rand, settings, end, new Cardinal[]{entrance});
    return prototype;
  }

  @Override
  public int getSize() {
    return createPrototype().getSize();
  }

  private DungeonBase createPrototype() {
    return getRoomSetting().instantiate();
  }

}
