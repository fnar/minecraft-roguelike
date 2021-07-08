package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonStorage extends DungeonBase {

  public DungeonStorage(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void pillarTop(WorldEditor editor, Theme theme, Coord cursor) {
    StairsBlock stair = theme.getSecondary().getStair();
    for (Direction dir : Direction.CARDINAL) {
      stair.setUpsideDown(true).setFacing(dir);
      cursor.translate(dir, 1);
      stair.stroke(editor, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  private static void pillar(WorldEditor editor, Coord base, Theme theme, int height) {
    Coord top = base.copy();
    top.up(height);
    RectSolid.newRect(base, top).fill(editor, theme.getSecondary().getPillar());
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    Theme theme = levelSettings.getTheme();
    List<Coord> chestSpaces = new ArrayList<>();

    Direction front = entrances.get(0);

    generateCavity(origin, front);
    generateFloor(origin, front);
    generateCeiling(origin, front);

    Coord cursor;
    Coord start;
    Coord end;
    BlockBrush wall = theme.getPrimary().getWall();
    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {

        cursor = origin.copy()
            .up(3)
            .translate(dir, 2)
            .translate(orthogonals, 2);
        pillarTop(worldEditor, theme, cursor);
        cursor.translate(dir, 3).translate(orthogonals, 3);
        pillarTop(worldEditor, theme, cursor);
        start = cursor.copy();

        cursor.down().translate(dir, 1);
        pillarTop(worldEditor, theme, cursor);

        end = cursor.copy().down(3).translate(dir, 1).translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(worldEditor, wall);

        cursor = origin.copy().translate(dir, 2).translate(orthogonals, 2);
        pillar(worldEditor, cursor, theme, 4);

        cursor.translate(dir, 4);
        pillar(worldEditor, cursor, theme, 3);

        cursor.up(2);
        pillarTop(worldEditor, theme, cursor);

        cursor.up(1);
        cursor.translate(dir.reverse(), 1);
        pillarTop(worldEditor, theme, cursor);

        cursor.translate(dir.reverse(), 3);
        pillarTop(worldEditor, theme, cursor);

        generateWall(origin, dir, orthogonals);

        cursor = origin.copy();
        cursor.translate(dir, 6);
        cursor.translate(orthogonals, 3);
        StairsBlock stair = theme.getSecondary().getStair();
        stair.setUpsideDown(true).setFacing(dir.reverse());
        stair.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);
        cursor.up(1);
        chestSpaces.add(cursor.copy());
        cursor.translate(orthogonals.reverse(), 1);
        chestSpaces.add(cursor.copy());

        start = origin.copy();
        start.down();
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = start.copy();
        end.translate(dir, 3);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getFloor());

        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(orthogonals, 5);
        pillar(worldEditor, cursor, theme, 4);

      }
    }

    chooseRandomLocations(2, chestSpaces)
        .forEach(coord -> worldEditor.getTreasureChestEditor()
            .createChest(coord, false, levelSettings.getDifficulty(origin), coord.dirTo(origin).reverse(), randomChestType()));

    generateDoorways(origin, entrances, getSize() - 3);

    return this;
  }

  private ChestType randomChestType() {
    return getRoomSetting().getChestType()
        .orElse(ChestType.chooseRandomAmong(worldEditor.getRandom(), ChestType.SUPPLIES_TREASURES));
  }

  private void generateWall(Coord origin, Direction dir, Direction orthogonals) {
    BlockBrush wall = levelSettings.getTheme().getPrimary().getWall();
    Coord start = origin.copy().translate(dir, 6).up(3);
    Coord end = start.copy().translate(orthogonals, 5);
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start.translate(dir, 1);
    end.translate(dir, 1).down(3);
    RectSolid.newRect(start, end).fill(worldEditor, wall, false, true);
  }

  private void generateCavity(Coord origin, Direction front) {
    int size = getSize() - 4; // 6
    RectSolid roomRect = RectSolid.newRect(
        origin.copy().translate(front, size).translate(front.left(), size).down(),
        origin.copy().translate(front.reverse(), size).translate(front.right(), size).up(getCeilingHeight())
    );
    SingleBlockBrush.AIR.fill(worldEditor, roomRect);
  }

  private void generateFloor(Coord origin, Direction front) {
    BlockBrush floor = levelSettings.getTheme().getPrimary().getFloor();
    RectSolid floorRect = RectSolid.newRect(
        origin.copy().translate(front, getSize()).translate(front.left(), getSize()).down(),
        origin.copy().translate(front.reverse(), getSize()).translate(front.right(), getSize()).down()
    );
    floor.fill(worldEditor, floorRect);
  }

  private void generateCeiling(Coord origin, Direction front) {
    BlockBrush wall = levelSettings.getTheme().getPrimary().getWall();
    RectSolid ceilingRect = RectSolid.newRect(
        origin.copy().translate(front, getSize()).translate(front.left(), getSize()).up(getCeilingHeight()),
        origin.copy().translate(front.reverse(), getSize()).translate(front.right(), getSize()).up(getCeilingHeight())
    );
    wall.fill(worldEditor, ceilingRect);
  }

  private int getCeilingHeight() {
    return 4;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
