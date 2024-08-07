package greymerk.roguelike.dungeon.segment.part;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.util.Pair;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.base.SecretRoom;
import greymerk.roguelike.dungeon.base.SecretsSetting;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class SegmentBase {

  public void generate(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord pos) {
    if (!level.containsRoomAt(pos) && isValidWall(editor, dir, pos)) {
      genWall(editor, level, dir, theme, pos);
    }
  }

  protected abstract void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord pos);

  protected boolean isValidWall(WorldEditor editor, Direction wallDirection, Coord pos) {
    return isValidNorthWall(wallDirection, editor, pos)
        || isValidSouthWall(wallDirection, editor, pos)
        || isValidEastWall(wallDirection, editor, pos)
        || isValidWestWall(wallDirection, editor, pos);
  }

  private boolean isValidNorthWall(Direction wallDirection, WorldEditor editor, Coord pos) {
    Coord northWest = new Coord(-1, 1, -2).translate(pos);
    Coord northEast = new Coord(1, 1, -2).translate(pos);
    return wallDirection == Direction.NORTH
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(northEast);
  }

  private boolean isValidSouthWall(Direction wallDirection, WorldEditor editor, Coord pos) {
    Coord southWest = new Coord(-1, 1, 2).translate(pos);
    Coord southEast = new Coord(1, 1, 2).translate(pos);
    return wallDirection == Direction.SOUTH
        && !editor.isAirBlock(southWest)
        && !editor.isAirBlock(southEast);
  }

  private boolean isValidEastWall(Direction wallDirection, WorldEditor editor, Coord pos) {
    Coord northWest = new Coord(2, 1, -1).translate(pos);
    Coord southEast = new Coord(2, 1, 1).translate(pos);
    return wallDirection == Direction.EAST
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(southEast);
  }

  private boolean isValidWestWall(Direction wallDirection, WorldEditor editor, Coord pos) {
    Coord northWest = new Coord(-2, 1, -1).translate(pos);
    Coord southWest = new Coord(-2, 1, 1).translate(pos);
    return wallDirection == Direction.WEST
        && !editor.isAirBlock(northWest)
        && !editor.isAirBlock(southWest);
  }

  public Optional<BaseRoom> generateSecret(SecretsSetting secretsSetting, WorldEditor worldEditor, LevelSettings levelSettings, Direction outwardFromSegment, Coord segmentCoord) {
    List<RoomSetting> secretRoomSettings = secretsSetting.getSecretRoomSettings();
    Optional<Pair<RoomSetting, SecretRoom>> first = secretRoomSettings.stream()
        .map(roomSetting -> new Pair<>(roomSetting, new SecretRoom(roomSetting, levelSettings, worldEditor)))
        .filter(pair -> pair.getValue().isValidLocation(segmentCoord, outwardFromSegment))
        .findFirst();
    first.ifPresent(pair -> secretRoomSettings.remove(pair.getKey()));
    return first.map(pair -> pair.getValue().generate(segmentCoord, Lists.newArrayList(outwardFromSegment)));
  }

  public StairsBlock getSecondaryStairs(Theme theme) {
    return theme.getSecondary().getStair();
  }

  protected BlockBrush getSecondaryWall(Theme theme) {
    return theme.getSecondary().getWall();
  }

  protected DoorBlock getSecondaryDoor(Theme theme) {
    return theme.getSecondary().getDoor();
  }

  protected BlockBrush getSecondaryLightBlock(Theme theme) {
    return theme.getSecondary().getLightBlock();
  }

  protected StairsBlock getPrimaryStairs(Theme theme) {
    return theme.getPrimary().getStair();
  }

  protected static BlockBrush getPrimaryWalls(Theme theme) {
    return theme.getPrimary().getWall();
  }

  protected BlockBrush getPrimaryPillar(Theme theme) {
    return theme.getPrimary().getPillar();
  }

  protected BlockBrush getSecondaryPillar(Theme theme) {
    return theme.getSecondary().getPillar();
  }

}
