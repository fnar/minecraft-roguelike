package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class CornerRoom extends DungeonBase {

  public CornerRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    BlockSet primary = levelSettings.getTheme().getPrimary();
    createHollowCenter(worldEditor, origin);
    createShell(worldEditor, origin, primary.getWall());
    fillFloor(worldEditor, origin, primary.getFloor());
    createCornerWalls(worldEditor, origin, primary);
    createCeiling(worldEditor, origin, primary);
    return this;
  }

  private void createCornerWalls(WorldEditor editor, Coord origin, BlockSet primary) {
    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = origin.copy()
          .translate(dir, 2)
          .translate(dir.antiClockwise(), 2);

      Coord pillarStart = cursor.copy();
      Coord pillarEnd = cursor.copy().up(2);
      RectSolid.newRect(pillarStart, pillarEnd).fill(editor, primary.getPillar());

      Coord pillarTop = cursor.copy().up();
      primary.getWall().stroke(editor, pillarTop);
    }
  }

  private void createCeiling(WorldEditor editor, Coord origin, BlockSet primary) {
    SingleBlockBrush.AIR.stroke(editor, origin.copy().up(4));

    primary.getWall().stroke(editor, origin.copy().up(5));

    for (Direction dir : Direction.CARDINAL) {
      Coord ceiling = origin.copy()
          .translate(dir, 1)
          .up(4);

      primary.getStair()
          .setUpsideDown(true)
          .setFacing(dir.reverse())
          .stroke(editor, ceiling);

      for (Direction orthogonal : dir.orthogonals()) {
        Coord decorativeCeiling = origin.copy()
            .translate(dir, 2)
            .translate(orthogonal, 1)
            .up(3);
        primary.getStair()
            .setUpsideDown(true)
            .setFacing(orthogonal.reverse())
            .stroke(editor, decorativeCeiling);
      }
    }
  }

  private void createHollowCenter(WorldEditor editor, Coord origin) {
    Coord hollowAirCorner0 = origin.add(-2, 0, -2);
    Coord hollowAirCorner1 = origin.add(2, 3, 2);
    RectSolid.newRect(hollowAirCorner0, hollowAirCorner1).fill(editor, SingleBlockBrush.AIR);
  }

  private void createShell(WorldEditor editor, Coord origin, BlockBrush blocks) {
    Coord roomShellCorner0 = origin.add(-3, -1, -3);
    Coord roomShellCorner1 = origin.add(3, 4, 3);
    RectHollow.newRect(roomShellCorner0, roomShellCorner1).fill(editor, blocks, false, true);
  }

  private void fillFloor(WorldEditor editor, Coord origin, BlockBrush floor) {
    Coord floorCorner0 = origin.add(-3, -1, -3);
    Coord floorCorner1 = origin.add(3, -1, 3);
    RectSolid.newRect(floorCorner0, floorCorner1).fill(editor, floor, false, true);
  }

  public int getSize() {
    return 4;
  }

}
