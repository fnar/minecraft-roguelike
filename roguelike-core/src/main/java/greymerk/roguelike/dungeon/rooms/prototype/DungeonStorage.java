package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
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

  private static void pillarTop(WorldEditor editor, ThemeBase theme, Coord cursor) {
    StairsBlock stair = theme.getSecondary().getStair();
    for (Direction dir : Direction.CARDINAL) {
      stair.setUpsideDown(true).setFacing(dir);
      cursor.translate(dir, 1);
      stair.stroke(editor, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  private static void pillar(WorldEditor editor, Coord base, ThemeBase theme, int height) {
    Coord top = base.copy();
    top.up(height);
    RectSolid.newRect(base, top).fill(editor, theme.getSecondary().getPillar());
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Random rand = worldEditor.getRandom(origin);

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

    for (Direction dir : Direction.CARDINAL) {
      for (Direction orthogonals : dir.orthogonals()) {

        cursor = new Coord(x, y, z);
        cursor.up(3);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 2);
        pillarTop(worldEditor, theme, cursor);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 3);
        pillarTop(worldEditor, theme, cursor);
        start = cursor.copy();

        cursor.down();
        cursor.translate(dir, 1);
        pillarTop(worldEditor, theme, cursor);

        end = cursor.copy();
        end.down(3);
        end.translate(dir, 1);
        end.translate(orthogonals, 1);
        RectSolid.newRect(start, end).fill(worldEditor, blocks);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 2);
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

        start = new Coord(x, y, z);
        start.translate(dir, 6);
        start.up(3);
        end = start.copy();
        end.translate(orthogonals, 5);
        RectSolid.newRect(start, end).fill(worldEditor, blocks);
        start.translate(dir, 1);
        end.translate(dir, 1);
        end.down(3);
        RectSolid.newRect(start, end).fill(worldEditor, blocks, false, true);

        cursor = new Coord(x, y, z);
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

        start = new Coord(x, y, z);
        start.down();
        start.translate(dir, 3);
        start.translate(orthogonals, 3);
        end = start.copy();
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
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, levelSettings.getDifficulty(origin), getRoomSetting().getChestType().orElse(ChestType.chooseRandomAmong(rand, ChestType.SUPPLIES_TREASURES)));
    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
