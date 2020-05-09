package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonCorner extends DungeonBase {

  public static final MetaBlock AIR = BlockType.get(BlockType.AIR);

  public DungeonCorner(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {
    BlockSet primary = settings.getTheme().getPrimary();
    createHollowCenter(editor, rand, origin);
    createShell(editor, rand, origin, primary.getWall());
    fillFloor(editor, rand, origin, primary.getFloor());
    createCornerWalls(editor, rand, origin, primary);
    createCeiling(editor, rand, origin, primary);
    return this;
  }

  private void createCornerWalls(IWorldEditor editor, Random rand, Coord origin, BlockSet primary) {
    for (Cardinal dir : Cardinal.directions) {
      Coord cursor = origin.copy()
          .translate(dir, 2)
          .translate(dir.antiClockwise(), 2);

      Coord pillarStart = cursor.copy();
      Coord pillarEnd = cursor.copy().up(2);
      RectSolid.fill(editor, rand, pillarStart, pillarEnd, primary.getPillar(), true, true);

      Coord pillarTop = cursor.copy().up(1);
      primary.getWall().set(editor, rand, pillarTop);
    }
  }

  private void createCeiling(IWorldEditor editor, Random rand, Coord origin, BlockSet primary) {
    AIR.set(editor, origin.copy().up(4));

    primary.getWall().set(editor, rand, origin.copy().up(5));

    for (Cardinal dir: Cardinal.directions) {
      Coord ceiling = origin.copy()
          .translate(dir, 1)
          .up(4);
      primary.getStair().setOrientation(dir.reverse(), true);
      primary.getStair().set(editor, rand, ceiling);

      for (Cardinal orthogonal : dir.orthogonal()) {
        Coord decorativeCeiling = origin.copy()
            .translate(dir, 2)
            .translate(orthogonal, 1)
            .up(3);
        primary.getStair().setOrientation(orthogonal.reverse(), true);
        primary.getStair().set(editor, rand, decorativeCeiling);
      }
    }
  }

  private void createHollowCenter(IWorldEditor editor, Random rand, Coord origin) {
    Coord hollowAirCorner0 = origin.add(-2, 0, -2);
    Coord hollowAirCorner1 = origin.add(2, 3, 2);
    RectSolid.fill(editor, rand, hollowAirCorner0, hollowAirCorner1, AIR);
  }

  private void createShell(IWorldEditor editor, Random rand, Coord origin, IBlockFactory blocks) {
    Coord roomShellCorner0 = origin.add(-3, -1, -3);
    Coord roomShellCorner1 = origin.add(3, 4, 3);
    RectHollow.fill(editor, rand, roomShellCorner0, roomShellCorner1, blocks, false, true);
  }

  private void fillFloor(IWorldEditor editor, Random rand, Coord origin, IBlockFactory floor) {
    Coord floorCorner0 = origin.add(-3, -1, -3);
    Coord floorCorner1 = origin.add(3, -1, 3);
    RectSolid.fill(editor, rand, floorCorner0, floorCorner1, floor, false, true);
  }

  public int getSize() {
    return 4;
  }

}
