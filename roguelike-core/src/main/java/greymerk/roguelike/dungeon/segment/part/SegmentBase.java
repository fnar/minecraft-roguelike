package greymerk.roguelike.dungeon.segment.part;

import com.google.common.collect.Lists;

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
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class SegmentBase {

  public void generate(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord pos) {
    if (!level.hasNearbyNode(pos) && isValidWall(editor, dir, pos)) {
      genWall(editor, level, dir, theme, pos);
    }
  }

  protected static void generateChest(WorldEditor worldEditor, Direction dir, Coord coord, ChestType[] defaultChestType) {
    chest(worldEditor, dir, coord, defaultChestType)
        .stroke(worldEditor, coord);
  }

  protected void generateTrappableChest(WorldEditor worldEditor, Direction dir, Coord coord, int difficulty, ChestType[] defaultChestType) {
    chest(worldEditor, dir, coord, defaultChestType)
        .withTrapBasedOnDifficulty(difficulty)
        .stroke(worldEditor, coord);
  }

  private static TreasureChest chest(WorldEditor worldEditor, Direction dir, Coord coord, ChestType[] defaultChestType) {
    return new TreasureChest(coord, worldEditor)
        .withChestType(ChestType.chooseRandomAmong(worldEditor.getRandom(), defaultChestType))
        .withFacing(dir);
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

  public Optional<BaseRoom> generateSecret(SecretsSetting secretsSetting, WorldEditor worldEditor, LevelSettings levelSettings, Direction dir, Coord pos) {
    List<RoomSetting> secretRoomSettings = secretsSetting.getSecretRoomSettings();
    Optional<Pair<RoomSetting, SecretRoom>> first = secretRoomSettings.stream()
        .map(roomSetting -> new Pair<>(roomSetting, new SecretRoom(roomSetting, levelSettings, worldEditor)))
        .filter(pair -> pair.getValue().isValidLocation(dir, pos))
        .findFirst();
    first.ifPresent(pair -> secretRoomSettings.remove(pair.getKey()));
    return first.map(pair -> pair.getValue().generate(pos, Lists.newArrayList(dir)));
  }

}
