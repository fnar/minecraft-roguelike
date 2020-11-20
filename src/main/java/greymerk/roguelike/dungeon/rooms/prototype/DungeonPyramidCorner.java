package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonPyramidCorner extends DungeonBase {

  public DungeonPyramidCorner(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    RectHollow.fill(editor, rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 3, z + 3), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 2, y + 3, z - 2), new Coord(x + 2, y + 5, z + 2), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 1, y + 3, z - 1), new Coord(x + 1, y + 3, z + 1), air, true, true);

    // floor
    RectSolid.fill(editor, rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    air.set(editor, cursor);


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      air.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.orthogonal()[0], 2);
      start = new Coord(cursor);
      cursor.translate(Cardinal.UP, 2);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
    }

    return this;
  }

  public int getSize() {
    return 4;
  }

}
