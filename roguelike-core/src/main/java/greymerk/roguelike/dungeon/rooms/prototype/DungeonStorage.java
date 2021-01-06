package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonStorage extends DungeonBase {

  public DungeonStorage(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void pillarTop(WorldEditor editor, ThemeBase theme, Coord cursor) {
    StairsBlock stair = theme.getSecondary().getStair();
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      stair.setUpsideDown(true).setFacing(dir);
      cursor.translate(dir, 1);
      stair.stroke(editor, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  private static void pillar(WorldEditor editor, Coord base, ThemeBase theme, int height) {
    Coord top = new Coord(base);
    top.translate(Cardinal.UP, height);
    RectSolid.newRect(base, top).fill(editor, theme.getSecondary().getPillar());
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    Random rand = worldEditor.getRandom();

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = levelSettings.getTheme();

    List<Coord> chestSpaces = new ArrayList<>();

    // space
    RectSolid.newRect(new Coord(x - 6, y, z - 6), new Coord(x + 6, y + 3, z + 6)).fill(worldEditor, SingleBlockBrush.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush blocks = theme.getPrimary().getWall();

    RectSolid.newRect(new Coord(x - 6, y - 1, z - 6), new Coord(x + 6, y - 1, z + 6)).fill(worldEditor, blocks);
    RectSolid.newRect(new Coord(x - 5, y + 4, z - 5), new Coord(x + 5, y + 4, z + 5)).fill(worldEditor, blocks);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orthogonals : dir.orthogonals()) {

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 2);
        pillarTop(worldEditor, theme, cursor);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 3);
        pillarTop(worldEditor, theme, cursor);
        start = new Coord(cursor);

        cursor.translate(Cardinal.DOWN, 1);
        cursor.translate(dir, 1);
        pillarTop(worldEditor, theme, cursor);

        end = new Coord(cursor);
        end.translate(Cardinal.DOWN, 3);
        end.translate(dir, 1);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(worldEditor, blocks);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 2);
        pillar(worldEditor, cursor, theme, 4);
        cursor.translate(dir, 4);
        pillar(worldEditor, cursor, theme, 3);


        cursor.translate(Cardinal.UP, 2);
        pillarTop(worldEditor, theme, cursor);

        cursor.translate(Cardinal.UP, 1);
        cursor.translate(dir.reverse(), 1);
        pillarTop(worldEditor, theme, cursor);

        cursor.translate(dir.reverse(), 3);
        pillarTop(worldEditor, theme, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 6);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(orthogonals, 5);
        RectSolid.newRect(start, end).fill(worldEditor, blocks);
        start.translate(dir, 1);
        end.translate(dir, 1);
        end.translate(Cardinal.DOWN, 3);
        RectSolid.newRect(start, end).fill(worldEditor, blocks, false, true);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 6);
        cursor.translate(orthogonals, 3);
        StairsBlock stair = theme.getSecondary().getStair();
        stair.setUpsideDown(true).setFacing(dir.reverse());
        stair.stroke(worldEditor, cursor);
        cursor.translate(orthogonals, 1);
        stair.stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP, 1);
        chestSpaces.add(new Coord(cursor));
        cursor.translate(orthogonals.reverse(), 1);
        chestSpaces.add(new Coord(cursor));

        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getFloor());

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 5);
        cursor.translate(orthogonals, 5);
        pillar(worldEditor, cursor, theme, 4);

      }
    }

    List<Coord> chestLocations = chooseRandomLocations(2, chestSpaces);
    worldEditor.getTreasureChestEditor().createChests(levelSettings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(rand, ChestType.SUPPLIES_TREASURES)));
    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
