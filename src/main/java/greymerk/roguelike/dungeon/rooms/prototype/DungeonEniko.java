package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;


public class DungeonEniko extends DungeonBase {

  public DungeonEniko(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void pillar(WorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, pillar, true, true);
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
    }
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();
    Coord start;
    Coord end;
    Coord cursor;
    List<Coord> chests = new ArrayList<>();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(6, -1, 6));
    end.translate(new Coord(-6, 4, -6));
    RectHollow.fill(editor, rand, start, end, walls, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(6, 4, 6));
    end.translate(new Coord(-6, 5, -6));
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(3, 4, 3));
    end.translate(new Coord(-3, 4, -3));
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.fill(editor, rand, start, end, floor, true, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      for (Cardinal o : dir.orthogonal()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        pillar(editor, rand, theme, c);

        c = new Coord(cursor);
        c.translate(o, 3);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.translate(o);
        stair.setOrientation(dir.reverse(), true).set(editor, c);
        c.translate(Cardinal.UP);
        chests.add(new Coord(c));
        c.translate(o.reverse());
        chests.add(new Coord(c));
      }

      cursor.translate(dir.antiClockwise(), 5);
      pillar(editor, rand, theme, cursor);

      if (Arrays.asList(entrances).contains(dir)) {
        start = new Coord(origin);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(dir, 6);
        RectSolid.fill(editor, rand, start, end, floor, true, true);
      }
    }

    generateSpawner(editor, rand, origin, settings.getDifficulty(origin), settings.getSpawners(), COMMON_MOBS);
    List<Coord> chestLocations = chooseRandomLocations(rand, 1, chests);
    editor.treasureChestEditor.createChests(settings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(rand, ChestType.COMMON_TREASURES)));

    return this;
  }

  public int getSize() {
    return 7;
  }
}
