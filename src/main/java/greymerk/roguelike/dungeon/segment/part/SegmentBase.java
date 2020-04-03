package greymerk.roguelike.dungeon.segment.part;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public abstract class SegmentBase implements ISegment {

  @Override
  public void generate(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos) {
    if (!level.hasNearbyNode(pos) && isValidWall(editor, dir, pos)) {
      genWall(editor, rand, level, dir, theme, pos);
    }
  }

  protected abstract void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos);

  protected boolean isValidWall(IWorldEditor editor, Cardinal wallDirection, Coord pos) {
    return isValidNorthWall(wallDirection, editor, pos)
        || isValidSouthWall(wallDirection, editor, pos)
        || isValidEastWall(wallDirection, editor, pos)
        || isValidWestWall(wallDirection, editor, pos);
  }

  private boolean isValidNorthWall(Cardinal wallDirection, IWorldEditor editor, Coord pos) {
    Coord northWest = new Coord(-1, 1, -2).add(pos);
    Coord northEast = new Coord(1, 1, -2).add(pos);
    return wallDirection == Cardinal.NORTH
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(northEast);
  }

  private boolean isValidSouthWall(Cardinal wallDirection, IWorldEditor editor, Coord pos) {
    Coord southWest = new Coord(-1, 1, 2).add(pos);
    Coord southEast = new Coord(1, 1, 2).add(pos);
    return wallDirection == Cardinal.SOUTH
        && !editor.isAirBlock(southWest)
        && !editor.isAirBlock(southEast);
  }

  private boolean isValidEastWall(Cardinal wallDirection, IWorldEditor editor, Coord pos) {
    Coord northWest = new Coord(2, 1, -1).add(pos);
    Coord southEast = new Coord(2, 1, 1).add(pos);
    return wallDirection == Cardinal.EAST
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(southEast);
  }

  private boolean isValidWestWall(Cardinal wallDirection, IWorldEditor editor, Coord pos) {
    Coord northWest = new Coord(-2, 1, -1).add(pos);
    Coord southWest = new Coord(-2, 1, 1).add(pos);
    return wallDirection == Cardinal.WEST
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(southWest);
  }

  public Optional<IDungeonRoom> generateSecret(SecretFactory secretFactory, IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos) {
    return secretFactory.getSecretRooms().stream()
        .filter(secretRoom -> secretRoom.isValid(editor, dir, pos))
        .findFirst()
        .map(secretRoom -> secretRoom.generate(editor, rand, settings, pos, dir));
  }

}
