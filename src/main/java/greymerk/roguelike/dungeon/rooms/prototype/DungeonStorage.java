package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.treasure.Treasure.SUPPLIES_TREASURES;

public class DungeonStorage extends DungeonBase {

  public DungeonStorage(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void pillarTop(WorldEditor editor, Random rand, ITheme theme, Coord cursor) {
    IStair step = theme.getSecondary().getStair();
    for (Cardinal dir : Cardinal.directions) {
      step.setOrientation(dir, true);
      cursor.translate(dir, 1);
      step.set(editor, rand, cursor, true, false);
      cursor.translate(dir.reverse(), 1);
    }
  }

  private static void pillar(WorldEditor editor, Random rand, Coord base, ITheme theme, int height) {
    Coord top = new Coord(base);
    top.translate(Cardinal.UP, height);
    RectSolid.fill(editor, rand, base, top, theme.getSecondary().getPillar());
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    List<Coord> chestSpaces = new ArrayList<>();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // space
    RectSolid.fill(editor, rand, new Coord(x - 6, y, z - 6), new Coord(x + 6, y + 3, z + 6), air);

    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory blocks = theme.getPrimary().getWall();

    RectSolid.fill(editor, rand, new Coord(x - 6, y - 1, z - 6), new Coord(x + 6, y - 1, z + 6), blocks);
    RectSolid.fill(editor, rand, new Coord(x - 5, y + 4, z - 5), new Coord(x + 5, y + 4, z + 5), blocks);

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : dir.orthogonal()) {

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(dir, 2);
        cursor.translate(orth, 2);
        pillarTop(editor, rand, theme, cursor);
        cursor.translate(dir, 3);
        cursor.translate(orth, 3);
        pillarTop(editor, rand, theme, cursor);
        start = new Coord(cursor);

        cursor.translate(Cardinal.DOWN, 1);
        cursor.translate(dir, 1);
        pillarTop(editor, rand, theme, cursor);

        end = new Coord(cursor);
        end.translate(Cardinal.DOWN, 3);
        end.translate(dir, 1);
        end.translate(orth, 1);
        RectSolid.fill(editor, rand, start, end, blocks);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 2);
        cursor.translate(orth, 2);
        pillar(editor, rand, cursor, theme, 4);
        cursor.translate(dir, 4);
        pillar(editor, rand, cursor, theme, 3);


        cursor.translate(Cardinal.UP, 2);
        pillarTop(editor, rand, theme, cursor);

        cursor.translate(Cardinal.UP, 1);
        cursor.translate(dir.reverse(), 1);
        pillarTop(editor, rand, theme, cursor);

        cursor.translate(dir.reverse(), 3);
        pillarTop(editor, rand, theme, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 6);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(orth, 5);
        RectSolid.fill(editor, rand, start, end, blocks);
        start.translate(dir, 1);
        end.translate(dir, 1);
        end.translate(Cardinal.DOWN, 3);
        RectSolid.fill(editor, rand, start, end, blocks, false, true);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 6);
        cursor.translate(orth, 3);
        IStair step = theme.getSecondary().getStair();
        step.setOrientation(dir.reverse(), true);
        step.set(editor, cursor);
        cursor.translate(orth, 1);
        step.set(editor, cursor);
        cursor.translate(Cardinal.UP, 1);
        chestSpaces.add(new Coord(cursor));
        cursor.translate(orth.reverse(), 1);
        chestSpaces.add(new Coord(cursor));

        start = new Coord(x, y, z);
        start.translate(Cardinal.DOWN, 1);
        start.translate(dir, 3);
        start.translate(orth, 3);
        end = new Coord(start);
        end.translate(dir, 3);
        end.translate(orth, 1);
        RectSolid.fill(editor, rand, start, end, theme.getSecondary().getFloor());

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 5);
        cursor.translate(orth, 5);
        pillar(editor, rand, cursor, theme, 4);

      }
    }

    List<Coord> chestLocations = chooseRandomLocations(rand, 2, chestSpaces);
    editor.treasureChestEditor.createChests(rand, settings.getDifficulty(origin), chestLocations, false, SUPPLIES_TREASURES);
    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
