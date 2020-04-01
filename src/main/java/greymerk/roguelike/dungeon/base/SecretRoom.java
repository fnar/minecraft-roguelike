package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SecretRoom implements ISecretRoom {

  DungeonRoom type;
  private int count;
  private IDungeonRoom prototype;

  public SecretRoom(DungeonRoom type, int count) {
    this.count = count;
    this.type = type;
    prototype = type.instantiate();
  }

  public SecretRoom(SecretRoom toCopy) {
    count = toCopy.count;
    prototype = toCopy.prototype;
    type = toCopy.type;
  }

  private boolean isValid(IWorldEditor editor, Random rand, Cardinal dir, Coord pos) {
    if (count <= 0) {
      return false;
    }
    Coord cursor = new Coord(pos);
    cursor.add(dir, prototype.getSize() + 5);

    return prototype.validLocation(editor, dir, cursor);
  }

  @Override
  public void add(int count) {
    this.count += count;
  }

  @Override
  public int getCount() {
    return count;
  }

  @Override
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos) {
    if (!isValid(editor, rand, dir, pos)) {
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

    start = new Coord(pos);
    end = new Coord(pos);
    end.add(dir, size + 5);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, pos, end, BlockType.get(BlockType.AIR));

    end.add(Cardinal.DOWN);
    prototype.generate(editor, rand, settings, new Cardinal[]{dir}, end);
    count -= 1;

    IDungeonRoom generated = prototype;
    prototype = type.instantiate();

    return generated;
  }

  @Override
  public boolean equals(Object o) {

    SecretRoom other = (SecretRoom) o;

    if (type != other.type) {
      return false;
    }

    return count == other.count;
  }
}
