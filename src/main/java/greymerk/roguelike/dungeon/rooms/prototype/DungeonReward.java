package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonReward extends DungeonBase {

  public DungeonReward(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = settings.getTheme();

    RectSolid.fill(editor, rand, new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 5, z + 7), BlockType.get(BlockType.AIR));
    RectHollow.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8), theme.getPrimary().getWall(), false, true);
    RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 5, z + 1), theme.getPrimary().getWall());

    Coord cursor;
    Coord start;
    Coord end;

    IStair stair = theme.getPrimary().getStair();

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      for (Cardinal orth : dir.orthogonal()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orth, 2);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP, 5);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.translate(dir.reverse());
        stair.setOrientation(dir.reverse(), false).set(editor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.translate(dir.reverse());
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.translate(Cardinal.UP);
        cursor.translate(dir.reverse());
        stair.setOrientation(dir.reverse(), true).set(editor, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(orth);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 8);
        cursor.translate(Cardinal.UP, 2);
        cursor.translate(orth);
        stair.setOrientation(orth.reverse(), true).set(editor, rand, cursor, true, false);
        cursor.translate(dir.reverse());
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir.reverse(), 2);
        stair.setOrientation(dir, true).set(editor, cursor);

        start = new Coord(x, y, z);
        start.translate(dir, 7);
        start.translate(orth, 3);
        start.translate(Cardinal.UP, 3);
        end = new Coord(start);
        end.translate(Cardinal.UP, 2);
        end.translate(orth, 2);
        theme.getPrimary().getPillar().fill(editor, rand, new RectSolid(start, end));

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orth, 3);
        stair.setOrientation(orth, false).set(editor, cursor);
        cursor.translate(orth, 2);
        stair.setOrientation(orth.reverse(), false).set(editor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(orth.reverse(), 2);
        stair.setOrientation(orth, true).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        stair.setOrientation(orth, true).set(editor, cursor);
        cursor.translate(orth, 2);
        stair.setOrientation(orth.reverse(), true).set(editor, cursor);
        cursor.translate(dir.reverse());
        cursor.translate(Cardinal.UP);
        end = new Coord(cursor);
        end.translate(orth.reverse(), 2);
        RectSolid.fill(editor, rand, cursor, end, stair.setOrientation(dir.reverse(), true), true, true);
        cursor.translate(Cardinal.UP);
        end.translate(Cardinal.UP);
        RectSolid.fill(editor, rand, cursor, end, theme.getPrimary().getWall(), true, true);
        end.translate(dir.reverse());
        stair.setOrientation(orth, true).set(editor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 7);
        cursor.translate(orth, 4);
        cursor.translate(Cardinal.DOWN);
        BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

      }

      Cardinal o = dir.antiClockwise();

      start = new Coord(x, y, z);
      start.translate(dir, 6);
      start.translate(o, 6);
      end = new Coord(start);
      end.translate(dir);
      end.translate(o);
      end.translate(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());

      cursor = new Coord(x, y, z);
      theme.getPrimary().getWall().set(editor, rand, cursor);
      cursor.translate(dir);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.translate(o);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.translate(Cardinal.UP, 4);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.translate(o.reverse());
      stair.setOrientation(dir, true).set(editor, cursor);

    }

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 4);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP);
    editor.treasureChestEditor.createChest(settings.getDifficulty(cursor), cursor, false, getRoomSetting().getChestType().orElse(ChestType.REWARD));
    return this;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
