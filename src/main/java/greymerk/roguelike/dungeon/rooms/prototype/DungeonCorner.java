package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonCorner extends DungeonBase {

  public DungeonCorner(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // fill air inside
    Coord hollowAirCorner0 = new Coord(x - 2, y, z - 2);
    Coord hollowAirCorner1 = new Coord(x + 2, y + 3, z + 2);
    RectSolid.fill(editor, rand, hollowAirCorner0, hollowAirCorner1, air);

    // shell
    Coord roomShellCorner0 = new Coord(x - 3, y - 1, z - 3);
    Coord roomShellCorner1 = new Coord(x + 3, y + 4, z + 3);
    RectHollow.fill(editor, rand, roomShellCorner0, roomShellCorner1, blocks, false, true);

    // floor
    Coord floorCorner0 = roomShellCorner0;
    Coord floorCorner1 = new Coord(x + 3, y - 1, z + 3);
    RectSolid.fill(editor, rand, floorCorner0, floorCorner1, theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    blocks.set(editor, rand, cursor);

    for (Cardinal dir : Cardinal.directions) {

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.left(), 2);
      start = new Coord(cursor);
      cursor.translate(Cardinal.UP, 2);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.translate(Cardinal.UP, 1);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 1);
      cursor.translate(Cardinal.UP, 4);
      stair.setOrientation(dir.reverse(), true);
      stair.set(editor, rand, cursor);

      for (Cardinal orth : dir.orthogonal()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 2);
        cursor.translate(orth, 1);
        cursor.translate(Cardinal.UP, 3);
        stair.setOrientation(orth.reverse(), true);
        stair.set(editor, rand, cursor);
      }
    }

    return this;
  }

  public int getSize() {
    return 4;
  }

}
